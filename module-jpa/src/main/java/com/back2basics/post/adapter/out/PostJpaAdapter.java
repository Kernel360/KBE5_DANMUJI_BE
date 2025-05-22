package com.back2basics.post.adapter.out;


import com.back2basics.model.post.Post;
import com.back2basics.port.out.post.PostRepositoryPort;
import com.back2basics.post.entity.PostEntity;
import com.back2basics.post.mapper.PostMapper;
import com.back2basics.post.repository.PostEntityRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostJpaAdapter implements PostRepositoryPort {

    private final PostEntityRepository postRepository;
    private final PostMapper mapper;

    @Override
    public Long save(Post post) {
        PostEntity entity = mapper.fromDomain(post);
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
        postRepository.save(mapper.fromDomain(post));
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}