package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostDeleteResult;

public interface DeletePostUseCase {

    PostDeleteResult softDeletePost(Long id, Long requesterId);
}