package com.back2basics.adapter.persistence.board.post.adapter;

import com.back2basics.adapter.persistence.board.post.PostEntityRepository;
import com.back2basics.adapter.persistence.board.post.PostMapper;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.out.PostUpdatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostUpdateJpaAdapter implements PostUpdatePort {

    private final PostEntityRepository postRepository;
    private final PostMapper mapper;

    @Override
    public Post update(Post post) {
        return mapper.toDomain(
            postRepository.save(mapper.toEntity(post))
        );
    }
}
