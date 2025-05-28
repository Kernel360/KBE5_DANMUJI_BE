package com.back2basics.adapter.persistence.post;

import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostLoadPort;
import com.back2basics.post.port.out.PostWritePort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostJpaAdapter implements PostLoadPort,
    PostWritePort {

    private final PostEntityRepository postEntityRepository;
    private final PostMapper postMapper;

    @Override
    public Long save(Post post) {
        PostEntity entity = postMapper.toEntity(post);
        return postEntityRepository.save(entity).getId();
    }

    @Override
    public void update(Post post) {
        PostEntity entity = postEntityRepository.findById(post.getId())
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        postMapper.updateEntityFields(entity, post);
        postEntityRepository.save(entity);
    }

    @Override
    public void softDelete(Long id) {
        PostEntity entity = postEntityRepository.findById(id)
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        entity.markDeleted();
        postEntityRepository.save(entity);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postEntityRepository.findActivePostById(id)
            .map(postMapper::toDomain);
    }

    @Override
    public Optional<Post> findByIdWithComments(Long id) {
        return postEntityRepository.findActivePostWithComments(id)
            .map(postMapper::toDomain);
    }

    @Override
    public List<Post> findAll() {
        return postEntityRepository.findAllActivePosts().stream()
            .map(postMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Post> findByAuthorId(Long authorId) {
        return postEntityRepository.findActivePostsByAuthorId(authorId).stream()
            .map(postMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Post> findActivePostsOrderByPriority() {
        return postEntityRepository.findActivePostsOrderByPriority().stream()
            .map(postMapper::toDomain)
            .collect(Collectors.toList());
    }
}