package com.back2basics.adapter.persistence.board.post.adapter;

import com.back2basics.adapter.persistence.board.post.PostEntity;
import com.back2basics.adapter.persistence.board.post.PostEntityRepository;
import com.back2basics.adapter.persistence.board.post.PostMapper;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.out.PostCreatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCreateJpaAdapter implements PostCreatePort {

    private final PostEntityRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Post save(Post post) {
        PostEntity saved = postRepository.save(postMapper.toEntity(post));
        return postMapper.toDomain(saved);
    }

}
