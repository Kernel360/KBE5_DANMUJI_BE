package com.back2basics.post.mapper;

import com.back2basics.model.post.Post;
import com.back2basics.post.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toDomain(PostEntity entity) {
        return Post.builder()
            .id(entity.getId())
            .authorName(entity.getAuthorName())
            .title(entity.getTitle())
            .content(entity.getContent())
            .type(entity.getType())
            .status(entity.getStatus())
            .priority(entity.getPriority())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .deletedAt(entity.getDeletedAt())
            .completedAt(entity.getCompletedAt())
            .build();
    }

    public PostEntity fromDomain(Post domain) {
        return PostEntity.builder()
            .id(domain.getId())
            .authorName(domain.getAuthorName())
            .title(domain.getTitle())
            .content(domain.getContent())
            .type(domain.getType())
            .status(domain.getStatus())
            .priority(domain.getPriority())
            .deletedAt(domain.getDeletedAt())
            .completedAt(domain.getCompletedAt())
            .build();
    }
}