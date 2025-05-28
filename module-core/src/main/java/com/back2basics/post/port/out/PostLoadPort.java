package com.back2basics.post.port.out;

import com.back2basics.post.model.Post;
import java.util.List;
import java.util.Optional;

public interface PostLoadPort {

    Optional<Post> findById(Long id);

    Optional<Post> findByIdWithComments(Long id);

    List<Post> findAll();

    List<Post> findByAuthorId(Long authorId);

    List<Post> findActivePostsOrderByPriority();

}
