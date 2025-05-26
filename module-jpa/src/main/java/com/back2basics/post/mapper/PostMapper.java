package com.back2basics.post.mapper;

import com.back2basics.comment.entity.CommentEntity;
import com.back2basics.comment.mapper.CommentMapper;
import com.back2basics.model.comment.Comment;
import com.back2basics.model.post.Post;
import com.back2basics.post.entity.PostEntity;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CommentMapper commentMapper;

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
            .comments(toDomainComments(entity.getComments()))
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
            .completedAt(domain.getCompletedAt())
            .comments(fromDomainComments(domain.getComments()))
            .build();
    }

    private List<Comment> toDomainComments(List<CommentEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream()
            .map(commentMapper::toDomain)
            .collect(Collectors.toList());
    }

    private List<CommentEntity> fromDomainComments(List<Comment> domains) {
        if (domains == null || domains.isEmpty()) {
            return Collections.emptyList();
        }
        return domains.stream()
            .map(commentMapper::fromDomain)
            .collect(Collectors.toList());
    }
}