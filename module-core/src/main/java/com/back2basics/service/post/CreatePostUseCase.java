package com.back2basics.service.post;

public interface CreatePostUseCase {

    Long createPost(PostCreateCommand command);
}