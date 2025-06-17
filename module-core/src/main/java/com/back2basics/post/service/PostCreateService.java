package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.post.file.File;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostCreateUseCase;
import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.port.out.PostCreatePort;
import com.back2basics.post.service.result.PostCreateResult;
import java.io.IOException;
import java.util.ArrayList;
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

    private final S3Util s3Util;

    @Override
    public PostCreateResult createPost(Long userId, Long projectId, Long projectStepId,
        String userIp, PostCreateCommand command, List<MultipartFile> files) throws IOException {

        userValidator.validateNotFoundUserId(userId);
        projectValidator.findProjectById(projectId);
        postValidator.findParentPost(command.getParentId());

        Post post = Post.create(command, userId, userIp);

        List<File> fileModels = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            List<String> uploadedUrls = s3Util.uploadFiles(files);
            for (int i = 0; i < files.size(); i++) {
                MultipartFile multipartFile = files.get(i);
                String fileUrl = uploadedUrls.get(i);

                fileModels.add(File.create(
                    null,
                    post.getId(),
                    multipartFile.getOriginalFilename(),
                    fileUrl,
                    extractExtension(multipartFile.getOriginalFilename()),
                    String.valueOf(multipartFile.getSize())
                ));
            }
        }

        postCreatePort.save(post, fileModels);
        return PostCreateResult.toResult(post);
    }

    private String extractExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
