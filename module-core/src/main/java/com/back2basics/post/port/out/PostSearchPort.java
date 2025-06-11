package com.back2basics.post.port.out;

import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchPort {

    Page<Post> search(String title, String clientCompany, String developerCompany,
        String author, Integer priority, PostStatus status, PostType type,
        Pageable pageable);
}
