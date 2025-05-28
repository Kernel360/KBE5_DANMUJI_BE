package com.back2basics.post.port.in;

import com.back2basics.post.port.in.command.PostCreateCommand;

public interface CreatePostUseCase {

    Long createPost(PostCreateCommand command);
}