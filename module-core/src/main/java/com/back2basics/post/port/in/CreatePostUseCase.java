package com.back2basics.post.port.in;

import com.back2basics.service.post.dto.PostCreateCommand;

public interface CreatePostUseCase {

    Long createPost(PostCreateCommand command);
}