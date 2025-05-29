package com.back2basics.adapter.persistence.post.adapter;

import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostCreatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCreateJpaAdapter implements PostCreatePort {

    private final PostEntityRepository postRepository;
    private final PostMapper mapper;

    @Override
    public void save(Post post) {
        postRepository.save(mapper.toEntity(post));
    }
}
