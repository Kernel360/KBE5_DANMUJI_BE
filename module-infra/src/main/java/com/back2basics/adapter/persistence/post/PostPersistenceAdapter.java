package com.back2basics.adapter.persistence.post;


import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostRepositoryPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostPersistenceAdapter implements PostRepositoryPort {

    private final PostEntityRepository postRepository;
    private final PostMapper mapper;

    @Override
    public Long save(Post post) {
        PostEntity entity = mapper.toEntity(post);
        return postRepository.save(entity).getId();
    }

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

    @Override
    public void update(Post post) {
        postRepository.save(mapper.toEntity(post));
    }


    @Override
    public void softDelete(Post post) {
        postRepository.save(mapper.toEntity(post));
    }
}