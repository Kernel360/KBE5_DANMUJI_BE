package com.back2basics.adapter.persistence.comment;

import com.back2basics.adapter.persistence.post.PostEntity;
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
            entity.getContent(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public CommentEntity toEntity(Comment domain) {
        CommentEntity entity = CommentEntity.of(domain);

        // todo : 게시글이랑 댓글 연관 끊고 이 이상한 코드 없앨거임
        PostEntity post = new PostEntity(
            domain.getPostId(),  // id
            null,                // parentId
            null,                // projectId
            null,                // authorIp
            null,                // author
            null,                // title
            null,                // content
            null,                // type
            null,                // priority
            null,                // projectStepId
            null                 // completedAt
        );
        
        return entity;
    }

}