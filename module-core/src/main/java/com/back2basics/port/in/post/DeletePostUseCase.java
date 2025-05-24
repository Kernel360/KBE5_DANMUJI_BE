package com.back2basics.port.in.post;

public interface DeletePostUseCase {

    void softDeletePost(Long id, String requesterName);
}