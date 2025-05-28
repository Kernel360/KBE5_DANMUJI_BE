package com.back2basics.post.port.in;

import com.back2basics.post.port.in.dto.PostCreateCommand;
import com.back2basics.post.port.in.dto.PostUpdateCommand;
import com.back2basics.post.service.result.PostCreateResult;
import com.back2basics.post.service.result.PostDeleteResult;
import com.back2basics.post.service.result.PostUpdateResult;

public interface PostCommandUseCase {

    PostCreateResult createPost(PostCreateCommand command);

    PostDeleteResult softDeletePost(Long id, Long requesterId);

    PostUpdateResult updatePost(Long id, PostUpdateCommand command);
}