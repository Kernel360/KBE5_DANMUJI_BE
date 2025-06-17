package com.back2basics.post.port.out;

import com.back2basics.post.model.Post;
import com.back2basics.post.service.result.ReadRecentPostResult;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReadPort {

    Optional<Post> findById(Long postId);

    Optional<Post> findPostByProjectStepId(Long postId, Long projectStepId);

    Page<Post> findAllPostsByProjectStepId(Long projectStepId, Pageable pageable);

    List<ReadRecentPostResult> getRecentPosts();
}
