package com.back2basics.post.port.in;

public interface PostDeleteUseCase {

    void softDeletePost(Long userId, Long projectStepId, Long postId);
}