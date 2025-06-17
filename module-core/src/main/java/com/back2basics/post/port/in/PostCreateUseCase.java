package com.back2basics.post.port.in;

import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.service.result.PostCreateResult;

public interface PostCreateUseCase {

    PostCreateResult createPost(Long userId, Long projectId, Long projectStepId, String userIp,
        PostCreateCommand command);
}