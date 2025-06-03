package com.back2basics.adapter.persistence.post;

import com.back2basics.comment.model.Comment;
import com.back2basics.post.model.Post;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    public Post toDomain(PostEntity entity, List<Comment> comments) {
        return Post.builder()
            .id(entity.getId())
            .authorId(entity.getAuthorId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .type(entity.getType())
            .status(entity.getStatus())
            .priority(entity.getPriority())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .deletedAt(entity.getDeletedAt())
            .completedAt(entity.getCompletedAt())
            .comments(comments)
            .build();
    }

    // 오버로딩용 메소드 (뭔가 더 좋은 방법이 있을거같음)
    public Post toDomain(PostEntity entity) {
        return Post.builder()
            .id(entity.getId())
            .authorId(entity.getAuthorId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .type(entity.getType())
            .status(entity.getStatus())
            .priority(entity.getPriority())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .deletedAt(entity.getDeletedAt())
            .completedAt(entity.getCompletedAt())
            .comments(new ArrayList<>())
            .build();
    }

    public PostEntity toEntity(Post domain) {

        PostEntity entity = PostEntity.builder()
            .id(domain.getId())
            .authorId(domain.getAuthorId())
            .title(domain.getTitle())
            .content(domain.getContent())
            .type(domain.getType())
            .status(domain.getStatus())
            .priority(domain.getPriority())
            .completedAt(domain.getCompletedAt())
            .build();

        if (domain.isDelete()) {
            entity.markDeleted();
        }

        return entity;
    }

    public void updateEntityFields(PostEntity entity, Post domain) {
        entity.update(
            domain.getTitle(),
            domain.getContent(),
            domain.getType(),
            domain.getStatus(),
            domain.getPriority(),
            domain.getCompletedAt(),
            domain.isDelete()
        );
    }
}