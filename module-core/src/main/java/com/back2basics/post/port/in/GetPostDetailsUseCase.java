package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostDetailsResult;

public interface GetPostDetailsUseCase {

    PostDetailsResult getPost(Long id);
}
