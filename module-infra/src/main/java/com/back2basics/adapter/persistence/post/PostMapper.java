package com.back2basics.adapter.persistence.post;

import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserMapper userMapper;

    public Post toDomain(PostEntity entity) {
        return Post.create(
            entity.getId(),
            entity.getParentId(),
            entity.getProjectId(),
            entity.getProjectStepId(),
            entity.getAuthorIp(),
            userMapper.toDomain(entity.getAuthor()),
            entity.getTitle(),
            entity.getContent(),
            entity.getType(),
            entity.getStatus(),
            entity.getPriority(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt(),
            entity.getCompletedAt()
        );
    }

    public PostEntity toEntity(Post domain) {

        UserEntity author = userMapper.toEntity(domain.getAuthor());
        PostEntity entity = PostEntity.of(domain, author);

        if (domain.isDelete()) {
            entity.markDeleted();
        }

        return entity;
    }

}