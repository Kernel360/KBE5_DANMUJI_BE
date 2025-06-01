package com.back2basics.adapter.persistence.post.adapter;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.mapper.PostMapper;
import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostSoftDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostDeleteJpaAdapter implements PostSoftDeletePort {

    private final PostEntityRepository postRepository;
    private final PostMapper mapper;

    @Override
    public void softDelete(Post post) {
        PostEntity entity = postRepository.findById(post.getId())
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        entity.markDeleted();
        postRepository.save(entity);
    }
}
