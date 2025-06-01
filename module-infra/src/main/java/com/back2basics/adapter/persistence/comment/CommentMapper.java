package com.back2basics.adapter.persistence.comment;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.comment.model.Comment;
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
            .postId(entity.getPost().getId())
            .parentCommentId(
                entity.getParentCommentId() != null ? entity.getParentCommentId().getId() : null)
            .authorId(entity.getAuthorId())
            .content(entity.getContent())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .children(children)
            .build();
    }

    public CommentEntity toEntity(Comment domain) {

        List<CommentEntity> children = domain.getChildren().stream()
            .map(this::toEntity)
            .collect(Collectors.toCollection(ArrayList::new));

        CommentEntity entity = CommentEntity.builder()
            .id(domain.getId())
            .authorId(domain.getAuthorId())
            .content(domain.getContent())
            .build();

        entity.assignPost(PostEntity.builder().id(domain.getPostId()).build());

        children.forEach(child -> entity.addChildComment(child));
        return entity;
    }
}