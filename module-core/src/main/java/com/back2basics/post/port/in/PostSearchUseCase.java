package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostListReadResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchUseCase {

    Page<PostListReadResult> searchPost(String keyword, Pageable pageable);
}
