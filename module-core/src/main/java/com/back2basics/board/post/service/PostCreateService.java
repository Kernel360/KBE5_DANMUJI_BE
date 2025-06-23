package com.back2basics.board.post.service;

import com.back2basics.board.file.model.File;
import com.back2basics.board.file.port.out.FileSavePort;
import com.back2basics.board.file.service.FileUploadService;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.in.PostCreateUseCase;
import com.back2basics.board.post.port.in.command.PostCreateCommand;
import com.back2basics.board.post.port.out.PostCreatePort;
import com.back2basics.board.post.service.notification.PostNotificationSender;
import com.back2basics.board.post.service.result.PostCreateResult;
import com.back2basics.history.model.HistoryRequestFactory;
import com.back2basics.history.service.HistoryCreateService;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.mention.MentionNotificationSender;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final HistoryCreateService historyCreateService;

    @Override
    public PostCreateResult createPost(Long userId, Long projectId, Long projectStepId,
        String userIp, PostCreateCommand command, List<MultipartFile> files) throws IOException {

        userValidator.validateNotFoundUserId(userId);
        projectValidator.findProjectById(projectId);
        postValidator.findParentPost(command.getParentId());

        Post post = Post.create(command, userId, userIp);
        Post savedPost = postCreatePort.save(post);

        uploadAndSaveFiles(files, savedPost.getId());
        postNotificationSender.sendNotification(userId, savedPost.getId(), command);
        mentionNotificationSender.notifyMentionedUsers(userId, savedPost.getId(),
            post.getContent());

        historyCreateService.create(HistoryRequestFactory.postCreated(savedPost));

        return PostCreateResult.toResult(savedPost);
    }

    private void uploadAndSaveFiles(List<MultipartFile> files, Long postId) throws IOException {
        if (files == null || files.isEmpty()) {
            return;
        }

        List<File> fileModels = fileUploadService.upload(files, postId);
        fileSavePort.saveAll(fileModels, postId);
    }


}
