package com.back2basics.board.post.port.out;

import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.service.result.ReadRecentPostResult;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReadPort {

    Optional<Post> findById(Long postId);

    Page<Post> findAllPostsByProjectIdAndStepId(Long projectId, Long projectStepId,
        Pageable pageable);

    List<ReadRecentPostResult> getRecentPosts();

    List<Post> getPostsWithProjectIdAndDueSoon(Long userId);

    List<Post> getHighPriorityPostsByUserId(Long userId);
}
