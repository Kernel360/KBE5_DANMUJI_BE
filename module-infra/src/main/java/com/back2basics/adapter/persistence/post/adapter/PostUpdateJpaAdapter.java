package com.back2basics.adapter.persistence.post.adapter;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostUpdatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostUpdateJpaAdapter implements PostUpdatePort {

    private final PostEntityRepository postRepository;
    private final PostMapper mapper;

    @Override
    public void update(Post post) {
        PostEntity entity = postRepository.findById(post.getId())
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        mapper.updateEntityFields(entity, post);
        postRepository.save(entity);
    }
}
