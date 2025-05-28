package com.back2basics.post.port.in;

import com.back2basics.post.port.in.command.PostUpdateCommand;
import com.back2basics.post.service.result.PostUpdateResult;

public interface UpdatePostUseCase {

    PostUpdateResult updatePost(Long id, PostUpdateCommand command);
}