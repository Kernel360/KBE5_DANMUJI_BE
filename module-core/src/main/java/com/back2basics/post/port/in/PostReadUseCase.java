package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostReadResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReadUseCase {

    PostReadResult getPost(Long id);

    Page<PostReadResult> getPostList(Pageable pageable);
}
