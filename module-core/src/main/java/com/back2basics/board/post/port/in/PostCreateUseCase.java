package com.back2basics.board.post.port.in;

import com.back2basics.board.post.port.in.command.PostCreateCommand;
import com.back2basics.board.post.service.result.PostCreateResult;
import com.back2basics.infra.s3.dto.PresignedUploadCompleteInfo;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostCreateUseCase {

    PostCreateResult createPost(Long userId, Long projectId, Long projectStepId, String userIp,
        PostCreateCommand command, List<MultipartFile> files) throws IOException;

    PostCreateResult createPostWithPresigned(Long userId, Long projectId, Long stepId,
        String userIp, PostCreateCommand command, List<PresignedUploadCompleteInfo> uploadedFiles);
}