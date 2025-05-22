package com.back2basics.port.out.post;

import com.back2basics.model.post.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepositoryPort {
    Long save(Post post);
    Optional<Post> findById(Long id);
    List<Post> findAll();
    void update(Post post);
    void deleteById(Long id);
}
