package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostReadUseCase;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.service.result.PostDetailsResult;
import com.back2basics.post.service.result.PostSimpleResult;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReadService implements PostReadUseCase {

    private final PostReadPort postReadPort;
    private final PostValidator postValidator;

    @Override
    public PostDetailsResult getPost(Long id) {
        Post post = postValidator.findPost(id);
        return PostDetailsResult.toResult(post);
    }

    @Override
    public List<PostSimpleResult> getPostList() {
        return postReadPort.findAll().stream()
            .map(PostSimpleResult::toResult)
            .collect(Collectors.toList());
    }
}
