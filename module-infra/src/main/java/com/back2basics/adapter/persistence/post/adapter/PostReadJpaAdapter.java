package com.back2basics.adapter.persistence.post.adapter;

import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostReadPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReadJpaAdapter implements PostReadPort {

    private final PostEntityRepository postRepository;
    private final PostMapper mapper;

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

}
