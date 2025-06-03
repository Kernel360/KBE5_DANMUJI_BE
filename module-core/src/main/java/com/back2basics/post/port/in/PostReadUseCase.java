package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostListReadResult;
import com.back2basics.post.service.result.PostReadResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReadUseCase {

    PostReadResult getPost(Long id);

    Page<PostListReadResult> getPostList(Pageable pageable);
}
