package com.back2basics.adapter.persistence.comment;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.comment.model.Comment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
            .parentCommentId(
                entity.getParentCommentId() != null ? entity.getParentCommentId().getId() : null)
            .author(userMapper.toDomain(entity.getAuthor()))
            .content(entity.getContent())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .children(new ArrayList<>())
            .build();
    }

    public List<Comment> toDomainHierarchy(List<CommentEntity> entities) {
        Map<Long, Comment> commentMap = new HashMap<>();
        for (CommentEntity entity : entities) {
            Comment comment = toDomain(entity);
            commentMap.put(comment.getId(), comment); // put:덮어쓰기
        }

        List<Comment> roots = new ArrayList<>();
        for (Comment comment : commentMap.values()) {
            Long parentId = comment.getParentCommentId();
            if (parentId == null) {
                roots.add(comment);
            } else {
                Comment parent = commentMap.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(comment);
                }
            }
        }

        return roots;
    }


    public CommentEntity toEntity(Comment domain) {

        List<CommentEntity> children = domain.getChildren().stream()
            .map(this::toEntity)
            .collect(Collectors.toCollection(ArrayList::new));

        CommentEntity entity = CommentEntity.builder()
            .id(domain.getId())
            .author(userMapper.toEntity(domain.getAuthor()))
            .content(domain.getContent())
            .build();

        entity.assignPost(PostEntity.builder().id(domain.getPostId()).build());

        children.forEach(child -> entity.addChildComment(child));
        return entity;
    }

}