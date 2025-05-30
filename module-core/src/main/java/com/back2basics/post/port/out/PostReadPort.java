package com.back2basics.post.port.out;

import com.back2basics.post.model.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReadPort {

    Optional<Post> findById(Long id);

    Page<Post> findAllWithPaging(Pageable pageable);
}
