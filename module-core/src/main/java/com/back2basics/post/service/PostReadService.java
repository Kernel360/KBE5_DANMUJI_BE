package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostReadUseCase;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.service.result.PostDetailReadResult;
import com.back2basics.post.service.result.PostSummaryReadResult;
import com.back2basics.post.service.result.ReadRecentPostResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReadService implements PostReadUseCase {

    private final PostReadPort postReadPort;
    private final PostValidator postValidator;

    @Override
    public PostDetailReadResult getPostById(Long userId, Long postId) {
        Post post = postValidator.findPost(postId);
        return PostDetailReadResult.toResult(post);
    }


    @Override
    public Page<PostSummaryReadResult> getAllPostsByProjectIdAndStepId(Long userId, Long projectId,
        Long projectStepId,
        Pageable pageable) {
        return postReadPort.findAllPostsByProjectIdAndStepId(projectId, projectStepId, pageable)
            .map(PostSummaryReadResult::toResult);
    }

    @Override
    public List<ReadRecentPostResult> getRecentPosts() {
        return postReadPort.getRecentPosts();
    }
}
