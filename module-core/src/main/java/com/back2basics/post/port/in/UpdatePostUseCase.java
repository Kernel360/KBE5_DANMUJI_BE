package com.back2basics.post.port.in;

import com.back2basics.service.post.dto.PostUpdateCommand;

public interface UpdatePostUseCase {

    void updatePost(Long id, PostUpdateCommand command);
}