package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.file.File;
import com.back2basics.post.file.FileDeletePort;
import com.back2basics.post.file.FileReadPort;
import com.back2basics.post.file.FileSavePort;
import com.back2basics.post.file.FileUploadService;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostUpdateUseCase;
import com.back2basics.post.port.in.command.PostUpdateCommand;
import com.back2basics.post.port.out.PostUpdatePort;
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

    @Override
    public void updatePost(Long userId, String userIp, Long postId,
        PostUpdateCommand command, List<MultipartFile> files) throws IOException {
        Post post = postValidator.findPost(postId);
        post.update(command, userIp);

        Post updatedPost = postUpdatePort.update(post);

        replaceFiles(files, updatedPost.getId());
    }

    private void replaceFiles(List<MultipartFile> files, Long postId) throws IOException {
        if (files == null) {
            return;
        }

        List<File> existingFiles = fileReadPort.getFilesByPostId(postId);

        fileDeletePort.deleteAllByPostId(postId);
        fileDeletePort.deleteFromStorage(existingFiles);

        if (!files.isEmpty()) {
            List<File> newFiles = fileUploadService.upload(files, postId);
            fileSavePort.saveAll(newFiles, postId);
        }
    }
}
