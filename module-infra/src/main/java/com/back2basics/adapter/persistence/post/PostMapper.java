package com.back2basics.adapter.persistence.post;

import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserMapper userMapper;

    public Post toDomain(PostEntity entity) {
        return Post.builder()
            .id(entity.getId())
            .parentId(entity.getParentId())
            .authorIp(entity.getAuthorIp())
            .author(userMapper.toDomain(entity.getAuthor()))
            .projectStepId(entity.getProjectStepId())
            .projectId(entity.getProjectId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .type(entity.getType())
            .status(entity.getStatus())
            .priority(entity.getPriority())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .deletedAt(entity.getDeletedAt())
            .completedAt(entity.getCompletedAt())
            .build();
    }


    public PostEntity toEntity(Post domain) {

        PostEntity entity = PostEntity.builder()
            .id(domain.getId())
            .parentId(domain.getParentId())
            .authorIp(domain.getAuthorIp())
            .author(userMapper.toEntity(domain.getAuthor()))
            .projectStepId(domain.getProjectStepId())
            .projectId(domain.getProjectId())
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

}