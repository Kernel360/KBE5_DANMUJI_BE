package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostUpdateUseCase;
import com.back2basics.post.port.in.command.PostUpdateCommand;
import com.back2basics.post.port.out.PostUpdatePort;
import com.back2basics.post.service.result.PostUpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostUpdateService implements PostUpdateUseCase {

    private final PostUpdatePort postUpdatePort;
    private final PostValidator postValidator;

    @Override
    public PostUpdateResult updatePost(Long id, PostUpdateCommand command) {
        Post post = postValidator.findPost(id);
        postValidator.isAuthor(post, command.getRequesterId());

        post.update(command);

        postUpdatePort.update(post);
        return PostUpdateResult.toResult(post);
    }
}
