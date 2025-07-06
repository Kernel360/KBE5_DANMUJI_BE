package com.back2basics.board.post.service;

import com.back2basics.board.link.service.LinkUpdateService;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.in.PostUpdateUseCase;
import com.back2basics.board.post.port.in.command.PostUpdateCommand;
import com.back2basics.board.post.port.out.PostUpdatePort;
import com.back2basics.file.model.ContentType;
import com.back2basics.file.model.File;
import com.back2basics.file.port.out.FileDeletePort;
import com.back2basics.file.port.out.FileReadPort;
import com.back2basics.file.port.out.FileSavePort;
import com.back2basics.file.service.FileUploadService;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.s3.dto.PresignedUploadCompleteInfo;
import com.back2basics.infra.validator.PostValidator;
import com.back2basics.mention.MentionNotificationSender;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostUpdateService implements PostUpdateUseCase {

    private final PostUpdatePort postUpdatePort;
    private final PostValidator postValidator;
    private final FileUploadService fileUploadService;
    private final FileSavePort fileSavePort;
    private final FileDeletePort fileDeletePort;
    private final FileReadPort fileReadPort;
    private final MentionNotificationSender mentionNotificationSender;
    private final HistoryLogService historyLogService;
    private final LinkUpdateService linkUpdateService;

    @Override
    public void updatePost(Long userId, String userIp, Long postId,
        PostUpdateCommand command, List<MultipartFile> files) throws IOException {
        Post post = postValidator.findPost(postId);
        Post beforePost = Post.copyOf(post);

        post.update(command, userIp);

        Post updatedPost = postUpdatePort.update(post);
        linkUpdateService.updateLinks(
            command.getNewLinks(),
            command.getLinkIdsToDelete(),
            updatedPost.getId()
        );

        mentionNotificationSender.notifyMentionedUsers(userId, post.getProjectId(), postId,
            post.getContent());

        replaceFiles(files, command.getFileIdsToDelete(), updatedPost.getId());

        historyLogService.logUpdated(DomainType.POST, userId, beforePost, updatedPost, "게시글 정보 수정");
    }

    private void replaceFiles(List<MultipartFile> files, List<Long> fileIdsToDelete, Long postId)
        throws IOException {

        List<Long> finalFileIdsToDelete = (fileIdsToDelete != null) ? fileIdsToDelete : List.of();

        List<File> existingFiles = fileReadPort.getFilesByReferenceId(postId);

        List<File> toDelete = existingFiles.stream()
            .filter(f -> finalFileIdsToDelete.contains(f.getId()))
            .toList();

        fileDeletePort.deleteFiles(toDelete);
        fileDeletePort.deleteFromStorage(toDelete);

        if (files != null && !files.isEmpty()) {
            List<File> newFiles = fileUploadService.upload(files, postId, ContentType.POST);
            fileSavePort.saveAll(newFiles, postId);
        }
    }

    @Override
    public void updatePostWithPresigned(Long userId, String userIp, Long postId,
        PostUpdateCommand command,
        List<PresignedUploadCompleteInfo> uploadedFiles) {
        Post post = postValidator.findPost(postId);
        Post beforePost = Post.copyOf(post);

        post.update(command, userIp);
        Post updatedPost = postUpdatePort.update(post);

        linkUpdateService.updateLinks(
            command.getNewLinks(),
            command.getLinkIdsToDelete(),
            updatedPost.getId()
        );

        mentionNotificationSender.notifyMentionedUsers(userId, post.getProjectId(), postId,
            post.getContent());

        replacePresignedFiles(uploadedFiles, command.getFileIdsToDelete(), updatedPost.getId());

        historyLogService.logUpdated(DomainType.POST, userId, beforePost, updatedPost, "게시글 정보 수정");
    }

    private void replacePresignedFiles(List<PresignedUploadCompleteInfo> uploadedFiles,
        List<Long> fileIdsToDelete, Long postId) {
        List<Long> finalFileIdsToDelete = (fileIdsToDelete != null) ? fileIdsToDelete : List.of();

        List<File> existingFiles = fileReadPort.getFilesByReferenceId(postId);

        List<File> toDelete = existingFiles.stream()
            .filter(f -> finalFileIdsToDelete.contains(f.getId()))
            .toList();

        fileDeletePort.deleteFiles(toDelete);
        fileDeletePort.deleteFromStorage(toDelete);

        if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
            List<File> newFiles = uploadedFiles.stream()
                .map(info -> File.create(
                    null,
                    ContentType.POST,
                    postId,
                    info.getOriginalName(),
                    info.getUrl(),
                    info.getExtension(),
                    info.getSize()
                ))
                .toList();
            fileSavePort.saveAll(newFiles, postId);
        }
    }

}
