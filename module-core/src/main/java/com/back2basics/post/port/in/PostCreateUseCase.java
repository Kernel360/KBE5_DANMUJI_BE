package com.back2basics.post.port.in;

import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.service.result.PostCreateResult;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostCreateUseCase {

    PostCreateResult createPost(Long userId, Long projectId, Long projectStepId, String userIp,
        PostCreateCommand command, List<MultipartFile> files) throws IOException;
}