package com.back2basics.port.out.post;

import com.back2basics.model.post.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepositoryPort {

    Long save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findAll();

    void update(Post post);

    void softDelete(Post post);
    // todo: update와 완전히 동일한 기능인데 나누어야할지? 그래도 메소드 명으로 명확히 하는게 나을지?
}
