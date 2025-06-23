package com.back2basics.board.post.service;

import com.back2basics.board.file.model.File;
import com.back2basics.board.file.port.out.FileDeletePort;
import com.back2basics.board.file.port.out.FileReadPort;
import com.back2basics.board.file.port.out.FileSavePort;
import com.back2basics.board.file.service.FileUploadService;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.in.PostUpdateUseCase;
import com.back2basics.board.post.port.in.command.PostUpdateCommand;
import com.back2basics.board.post.port.out.PostUpdatePort;
import com.back2basics.history.model.HistoryRequestFactory;
import com.back2basics.history.service.HistoryCreateService;
import com.back2basics.infra.validation.validator.PostValidator;
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
    private final HistoryCreateService historyCreateService;

    @Override
    public void updatePost(Long userId, String userIp, Long postId,
        PostUpdateCommand command, List<MultipartFile> files) throws IOException {
        Post post = postValidator.findPost(postId);
        Post beforePost = Post.copyOf(post);

        post.update(command, userIp);

        Post updatedPost = postUpdatePort.update(post);
        mentionNotificationSender.notifyMentionedUsers(userId, postId, post.getContent());

        replaceFiles(files, command.getFileIdsToDelete(), updatedPost.getId());

        historyCreateService.create(HistoryRequestFactory.postUpdated(beforePost, updatedPost));
    }

    private void replaceFiles(List<MultipartFile> files, List<Long> fileIdsToDelete, Long postId)
        throws IOException {

        List<Long> finalFileIdsToDelete = (fileIdsToDelete != null) ? fileIdsToDelete : List.of();

        List<File> existingFiles = fileReadPort.getFilesByPostId(postId);

        List<File> toDelete = existingFiles.stream()
            .filter(f -> finalFileIdsToDelete.contains(f.getId()))
            .toList();

        fileDeletePort.deleteFiles(toDelete);
        fileDeletePort.deleteFromStorage(existingFiles);

        if (files != null && !files.isEmpty()) {
            List<File> newFiles = fileUploadService.upload(files, postId);
            fileSavePort.saveAll(newFiles, postId);
        }
    }
}
