package com.back2basics.comment.mapper;

import com.back2basics.comment.entity.CommentEntity;
import com.back2basics.model.comment.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toDomain(CommentEntity entity) {

        List<Comment> children = entity.getChildrenComments().stream()
            .map(this::toDomain)
            .collect(Collectors.toCollection(ArrayList::new));

        return Comment.builder()
            .id(entity.getId())
            .postId(entity.getPost() != null ? entity.getPost().getId() : null)
            .parentCommentId(
                entity.getParentCommentId() != null ? entity.getParentCommentId().getId() : null)
            .authorName(entity.getAuthorName())
            .content(entity.getContent())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .children(children)
            .build();
    }

    public CommentEntity fromDomain(Comment domain) {
        return CommentEntity.builder()
            .id(domain.getId())
            .authorName(domain.getAuthorName())
            .content(domain.getContent())
            .build();
    }
}