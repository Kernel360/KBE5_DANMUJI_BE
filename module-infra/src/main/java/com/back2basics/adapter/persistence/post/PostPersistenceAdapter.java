package com.back2basics.adapter.persistence.post;


import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostCreatePort;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.port.out.PostSoftDeletePort;
import com.back2basics.post.port.out.PostUpdatePort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostPersistenceAdapter implements PostCreatePort,
    PostReadPort,
    PostUpdatePort,
    PostSoftDeletePort {

    private final PostEntityRepository postRepository;
    private final PostMapper mapper;

    @Override
    public void save(Post post) {
        postRepository.save(mapper.toEntity(post));
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

        Optional<PostEntity> existingEntity = postRepository.findById(post.getId());
        if (existingEntity.isPresent()) {
            PostEntity entityToUpdate = existingEntity.get();
            entityToUpdate.markDeleted();
            postRepository.save(entityToUpdate);
        } else {
            postRepository.save(mapper.toEntity(post));
        }

    }
}