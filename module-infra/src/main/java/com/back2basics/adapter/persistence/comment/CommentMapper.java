package com.back2basics.adapter.persistence.comment;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.comment.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public Comment toDomain(CommentEntity entity) {
        return Comment.builder()
            .id(entity.getId())
            .postId(entity.getPost().getId())
            .parentCommentId(entity.getParentId())
            .authorIp(entity.getAuthorIp())
            .author(userMapper.toDomain(entity.getAuthor()))
            .content(entity.getContent())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    public CommentEntity toEntity(Comment domain) {
        CommentEntity entity = CommentEntity.builder()
            .id(domain.getId())
            .parentId(domain.getParentCommentId())
            .authorIp(domain.getAuthorIp())
            .author(userMapper.toEntity(domain.getAuthor()))
            .content(domain.getContent())
            .build();

        entity.assignPost(PostEntity.builder().id(domain.getPostId()).build());
        return entity;
    }

}