package com.back2basics.service.post;

public interface UpdatePostUseCase {

    void updatePost(Long id, PostUpdateCommand command);
}