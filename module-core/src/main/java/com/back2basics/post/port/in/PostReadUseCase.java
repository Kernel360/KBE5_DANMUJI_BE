package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostReadResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReadUseCase {

    PostReadResult getPost(Long userId, Long postId);

    Page<PostReadResult> getPostList(Long userId, Long projectId, Long projectStepId,
        Pageable pageable);
}
