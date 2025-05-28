package com.back2basics.post.service;

import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostQueryUseCase;
import com.back2basics.post.port.out.PostLoadPort;
import com.back2basics.post.service.result.PostDetailsResult;
import com.back2basics.post.service.result.PostSimpleResult;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostQueryService implements PostQueryUseCase {

    private final PostLoadPort postLoadPort;

    @Override
    public PostDetailsResult getPost(Long id) {
        Post post = postLoadPort.findByIdWithComments(id)
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        return PostDetailsResult.toResult(post);
    }

    @Override
    public List<PostSimpleResult> getPostList() {
        List<Post> posts = postLoadPort.findActivePostsOrderByPriority();

        return posts.stream()
            .map(PostSimpleResult::toResult)
            .collect(Collectors.toList());
    }
}