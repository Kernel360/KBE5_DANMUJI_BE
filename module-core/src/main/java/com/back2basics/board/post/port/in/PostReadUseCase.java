package com.back2basics.board.post.port.in;

import com.back2basics.board.post.service.result.PostDetailReadResult;
import com.back2basics.board.post.service.result.PostSummaryReadResult;
import com.back2basics.board.post.service.result.ReadRecentPostResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReadUseCase {

    PostDetailReadResult getPostById(Long userId, Long postId);

    Page<PostSummaryReadResult> getAllPostsByProjectIdAndStepId(
        Long userId,
        Long projectId,
        Long projectStepId,
        Pageable pageable);

    List<ReadRecentPostResult> getRecentPosts();
}
