package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostReadResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchUseCase {

    Page<PostReadResult> searchPost(Long userId, String keyword, Pageable pageable);
}
