package com.back2basics.post.port.in;

public interface DeletePostUseCase {

    void softDeletePost(Long id, String requesterName);
}