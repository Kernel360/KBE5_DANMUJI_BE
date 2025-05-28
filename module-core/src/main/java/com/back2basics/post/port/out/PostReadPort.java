package com.back2basics.post.port.out;

import com.back2basics.post.model.Post;
import java.util.List;
import java.util.Optional;

public interface PostReadPort {

    Optional<Post> findById(Long id);

    List<Post> findAll();
}
