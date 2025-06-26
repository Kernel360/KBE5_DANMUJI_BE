package com.back2basics.adapter.persistence.comment;

import com.back2basics.adapter.persistence.comment.projection.CommentWithPostAndAuthorResult;
import com.back2basics.comment.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {


    public Comment toDomain(CommentEntity entity) {
        return Comment.create(
            entity.getId(),
            entity.getParentId(),
            entity.getPostId(),
            entity.getAuthorIp(),
            entity.getAuthorId(),
            null,
            null,
            entity.getContent(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public Comment toDomain(CommentWithPostAndAuthorResult result) {
        return Comment.create(
            result.commentId(),
            result.parentId(),
            result.postId(),
            result.authorIp(),
            result.authorId(),
            result.authorName(),
            result.authorUsername(),
            result.content(),
            result.createdAt(),
            result.updatedAt()
        );
    }

    public CommentEntity toEntity(Comment domain) {
        CommentEntity entity = CommentEntity.of(domain);

        return entity;
    }

}