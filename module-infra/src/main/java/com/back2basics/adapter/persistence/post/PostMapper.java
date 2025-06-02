package com.back2basics.adapter.persistence.post;

import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.comment.model.Comment;
import com.back2basics.post.model.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CommentMapper commentMapper;

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

    public Post toDomainList(PostEntity entity) {
        return Post.builder()
            .id(entity.getId())
            .authorId(entity.getAuthorId())
            .title(entity.getTitle())
            .type(entity.getType())
            .status(entity.getStatus())
            .priority(entity.getPriority())
            .createdAt(entity.getCreatedAt())
            .completedAt(entity.getCompletedAt())
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