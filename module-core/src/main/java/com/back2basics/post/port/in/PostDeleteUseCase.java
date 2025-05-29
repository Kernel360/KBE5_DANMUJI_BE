package com.back2basics.post.port.in;

public interface PostDeleteUseCase {

    void softDeletePost(Long id, Long requesterId);
}