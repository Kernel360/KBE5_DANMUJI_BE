package com.back2basics.post.port.out;

import com.back2basics.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchPort {

    Page<Post> searchByKeyword(String keyword, Pageable pageable);
}
