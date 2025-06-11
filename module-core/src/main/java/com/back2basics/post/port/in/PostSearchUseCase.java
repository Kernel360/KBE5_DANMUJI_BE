package com.back2basics.post.port.in;

import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.service.result.PostReadResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchUseCase {

    Page<PostReadResult> searchPost(Long userId, String title, String clientCompany,
        String developerCompany, String author, Integer priority, PostStatus status,
        PostType type, Pageable pageable);
}
