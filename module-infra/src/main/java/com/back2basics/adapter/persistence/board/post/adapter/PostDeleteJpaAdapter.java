package com.back2basics.adapter.persistence.board.post.adapter;

import com.back2basics.adapter.persistence.board.post.PostEntity;
import com.back2basics.adapter.persistence.board.post.PostEntityRepository;
import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.out.PostSoftDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostDeleteJpaAdapter implements PostSoftDeletePort {

    private final PostEntityRepository postRepository;

    @Override
    public void softDelete(Post post) {
        PostEntity entity = postRepository.findById(post.getId())
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        entity.markDeleted();
        postRepository.save(entity);
    }
}
