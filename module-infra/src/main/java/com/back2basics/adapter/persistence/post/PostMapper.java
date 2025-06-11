package com.back2basics.adapter.persistence.post;

import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;
    private final ProjectStepMapper projectStepMapper;

    public Post toDomain(PostEntity entity) {
        return Post.builder()
            .id(entity.getId())
            .parentId(entity.getParentId())
            .authorIp(entity.getAuthorIp())
            .author(userMapper.toDomain(entity.getAuthor()))
            .project(projectMapper.toDomain(entity.getProject()))
            .projectStep(projectStepMapper.toDomain(entity.getProjectStep()))
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
            .project(projectMapper.fromDomain(
                domain.getProject())) // todo : toEntity, fromDomain 같은 기능인데 메소드 이름 다름. 통일 필요
            .projectStep(projectStepMapper.toEntity(
                domain.getProjectStep()))
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