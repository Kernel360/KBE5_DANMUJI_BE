package com.back2basics.adapter.persistence.post;

import com.back2basics.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    public Post toDomain(PostEntity entity) {
        return Post.create(
            entity.getId(),
            entity.getParentId(),
            entity.getProjectId(),
            entity.getProjectStepId(),
            entity.getAuthorIp(),
            entity.getAuthorId(),
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

        PostEntity entity = PostEntity.of(domain);

        if (domain.isDelete()) {
            entity.markDeleted();
        }

        return entity;
    }

}