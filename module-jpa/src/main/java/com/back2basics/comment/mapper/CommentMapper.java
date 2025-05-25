package com.back2basics.comment.mapper;

import com.back2basics.comment.entity.CommentEntity;
import com.back2basics.model.comment.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toDomain(CommentEntity entity) {
        return Comment.builder()
            .id(entity.getId())
            .postId(entity.getPostId())
            .parentId(entity.getParentId())
            .authorName(entity.getAuthorName())
            .content(entity.getContent())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    public CommentEntity fromDomain(Comment domain) {
        return CommentEntity.builder()
            .id(domain.getId())
            .postId(domain.getPostId())
            .parentId(domain.getParentId())
            .authorName(domain.getAuthorName())
            .content(domain.getContent())
            .build();
    }
}