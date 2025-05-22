package com.back2basics.port.in.post;

import com.back2basics.service.post.dto.PostCreateCommand;

public interface CreatePostUseCase {

    Long createPost(PostCreateCommand command);
}