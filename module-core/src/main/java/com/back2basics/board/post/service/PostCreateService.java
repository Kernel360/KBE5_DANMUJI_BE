package com.back2basics.board.post.service;

import com.back2basics.board.link.service.LinkCreateService;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.in.PostCreateUseCase;
import com.back2basics.board.post.port.in.command.PostCreateCommand;
import com.back2basics.board.post.port.out.PostCreatePort;
import com.back2basics.board.post.service.notification.PostNotificationSender;
import com.back2basics.board.post.service.result.PostCreateResult;
import com.back2basics.file.model.ContentType;
import com.back2basics.file.model.File;
import com.back2basics.file.port.out.FileSavePort;
import com.back2basics.file.service.FileUploadService;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.s3.dto.PresignedUploadCompleteInfo;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.mention.MentionNotificationSender;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostCreateService implements PostCreateUseCase {

    private final PostCreatePort postCreatePort;
    private final ProjectValidator projectValidator;
    private final PostValidator postValidator;
    private final UserValidator userValidator;
    private final FileUploadService fileUploadService;
    private final FileSavePort fileSavePort;
    private final PostNotificationSender postNotificationSender;
    private final MentionNotificationSender mentionNotificationSender;
    private final HistoryLogService historyLogService;
    private final LinkCreateService linkCreateService;

    @Override
    public PostCreateResult createPost(Long userId, Long projectId, Long projectStepId,
        String userIp, PostCreateCommand command, List<MultipartFile> files) throws IOException {

        userValidator.validateNotFoundUserId(userId);
        projectValidator.findById(projectId);
        postValidator.findParentPost(command.getParentId());

        Post post = Post.createFromCommand(command, userId, userIp);
        Post savedPost = postCreatePort.save(post);

        uploadAndSaveFiles(files, savedPost.getId());
        linkCreateService.createLinks(command.getNewLinks(), savedPost.getId());

        postNotificationSender.sendNotification(userId, savedPost.getId(), command);
        mentionNotificationSender.notifyMentionedUsers(userId, projectId, savedPost.getId(),
            post.getContent());

        historyLogService.logCreated(DomainType.POST, userId, savedPost, "게시글 생성");
        return PostCreateResult.toResult(savedPost);
    }

    @Override
    @Transactional
    public PostCreateResult createPostWithPresigned(Long userId, Long projectId, Long stepId,
        String userIp, PostCreateCommand command, List<PresignedUploadCompleteInfo> uploadedFiles) {

        userValidator.validateNotFoundUserId(userId);
        projectValidator.findById(projectId);
        postValidator.findParentPost(command.getParentId());

        Post post = Post.createFromCommand(command, userId, userIp);
        Post savedPost = postCreatePort.save(post);

        log.info("======================== createPostWithPresigned() 의 url : {}",
            uploadedFiles.get(0).getUrl());

        saveFilesFromPresignedUrls(uploadedFiles, savedPost.getId());

        linkCreateService.createLinks(command.getNewLinks(), savedPost.getId());
        postNotificationSender.sendNotification(userId, savedPost.getId(), command);
        mentionNotificationSender.notifyMentionedUsers(userId, projectId, savedPost.getId(),
            post.getContent());
        historyLogService.logCreated(DomainType.POST, userId, savedPost, "게시글 생성");

        return PostCreateResult.toResult(savedPost);
    }

    private void saveFilesFromPresignedUrls(List<PresignedUploadCompleteInfo> uploadedFiles,
        Long postId) {
        if (uploadedFiles == null || uploadedFiles.isEmpty()) {
            return;
        }

        log.info("======================== saveFilesFromPresignedUrls() 의 url : {}",
            uploadedFiles.get(0).getUrl());
        List<File> fileModels = uploadedFiles.stream()
            .map(info -> File.create(
                null,
                ContentType.POST,
                postId,
                info.getOriginalName(),
                info.getUrl(),
                info.getExtension(),
                info.getSize()
            )).toList();
        fileSavePort.saveAll(fileModels, postId);
        log.info(
            "======================== after fileSavePort.saveAll(fileModels, postId)의 url : {}",
            uploadedFiles.get(0).getUrl());
    }

    private void uploadAndSaveFiles(List<MultipartFile> files, Long postId) throws IOException {
        if (files == null || files.isEmpty()) {
            return;
        }

        List<File> fileModels = fileUploadService.upload(files, postId);
        fileSavePort.saveAll(fileModels, postId);
    }


}
