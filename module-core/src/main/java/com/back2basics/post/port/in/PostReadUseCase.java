package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostReadResult;
import com.back2basics.post.service.result.ReadRecentPostResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReadUseCase {

    PostReadResult getPostById(Long userId, Long postId);

    Page<PostReadResult> getAllPostsByProjectStepId(Long userId, Long projectStepId,
        Pageable pageable);

    List<ReadRecentPostResult> getRecentPosts();
}
