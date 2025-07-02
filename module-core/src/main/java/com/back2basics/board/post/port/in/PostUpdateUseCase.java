package com.back2basics.board.post.port.in;

import com.back2basics.board.post.port.in.command.PostUpdateCommand;
import com.back2basics.infra.s3.dto.PresignedUploadCompleteInfo;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostUpdateUseCase {

    void updatePost(
        Long userId,
        String userIp,
        Long postId,
        PostUpdateCommand command,
        List<MultipartFile> files
    ) throws IOException;

    void updatePostWithPresigned(
        Long userId,
        String userIp,
        Long postId,
        PostUpdateCommand command,
        List<PresignedUploadCompleteInfo> uploadedFiles
    );
}