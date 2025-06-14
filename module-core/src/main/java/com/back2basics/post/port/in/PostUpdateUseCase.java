package com.back2basics.post.port.in;

import com.back2basics.post.port.in.command.PostUpdateCommand;

public interface PostUpdateUseCase {

    void updatePost(Long userId, String userIp, Long postId, PostUpdateCommand command);
}