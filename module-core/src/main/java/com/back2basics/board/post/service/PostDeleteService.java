package com.back2basics.board.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.in.PostDeleteUseCase;
import com.back2basics.board.post.port.out.PostSoftDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDeleteService implements PostDeleteUseCase {

    private final PostSoftDeletePort postSoftDeletePort;
    private final PostValidator postValidator;

    @Override
    public void softDeletePost(Long requesterId, Long postId) {
        Post post = postValidator.findPost(postId);
        postValidator.isAuthor(post, requesterId);

        post.markDeleted();

        postSoftDeletePort.softDelete(post);
    }
}
