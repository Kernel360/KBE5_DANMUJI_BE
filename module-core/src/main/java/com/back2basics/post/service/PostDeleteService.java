package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostDeleteUseCase;
import com.back2basics.post.port.out.PostSoftDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDeleteService implements PostDeleteUseCase {

    private final PostSoftDeletePort postSoftDeletePort;
    private final PostValidator postValidator;

    @Override
    public void softDeletePost(Long id, Long requesterId) {
        Post post = postValidator.findPost(id);
        postValidator.isAuthor(post, requesterId);

        post.markDeleted();

        postSoftDeletePort.softDelete(post);
    }
}
