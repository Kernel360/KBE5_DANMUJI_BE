package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostReadUseCase;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.service.result.PostListReadResult;
import com.back2basics.post.service.result.PostReadResult;
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
    public PostReadResult getPost(Long id) {
        Post post = postValidator.findPost(id);
        return PostReadResult.toResult(post);
    }

    @Override
    public Page<PostListReadResult> getPostList(Pageable pageable) {
        return postReadPort.findAllWithPaging(pageable)
            .map(PostListReadResult::toResult);
    }
}
