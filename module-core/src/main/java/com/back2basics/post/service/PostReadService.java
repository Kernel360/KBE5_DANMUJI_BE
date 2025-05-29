package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostReadUseCase;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.service.result.PostReadResult;
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
    public PostReadResult getPost(Long id) {
        Post post = postValidator.findPost(id);
        return PostReadResult.toResult(post);
    }

    @Override
    public List<PostReadResult> getPostList() {
        return postReadPort.findAll().stream()
            .map(PostReadResult::toResult)
            .collect(Collectors.toList());
    }
}
