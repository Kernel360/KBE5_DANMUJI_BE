package com.back2basics.adapter.persistence.post;

import com.back2basics.adapter.persistence.post.adapter.projection.PostDetailResult;
import com.back2basics.adapter.persistence.post.adapter.projection.PostSummaryResult;
import com.back2basics.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    public Post toDomain(PostEntity entity) {
        return Post.create(
            entity.getId(),
            entity.getParentId(),
            entity.getProjectId(),
            entity.getProjectStepId(),
            entity.getAuthorIp(),
            entity.getAuthorId(),
            null,
            entity.getTitle(),
            entity.getContent(),
            entity.getType(),
            entity.getPriority(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
        );
    }

    public PostEntity toEntity(Post domain) {

        PostEntity entity = PostEntity.of(domain);

        if (domain.isDelete()) {
            entity.markDeleted();
        }

        return entity;
    }

    public Post toDomain(PostDetailResult result) {
        return Post.create(
            result.postId(),
            result.parentId(),
            result.projectId(),
            result.projectStepId(),
            result.authorIp(),
            result.authorId(),
            result.authorName(),
            result.title(),
            result.content(),
            result.type(),
            result.priority(),
            result.createdAt(),
            result.updatedAt()
        );
    }

    public Post toDomain(PostSummaryResult result) {
        return Post.create(
            result.postId(),
            result.projectId(),
            result.parentId(),
            result.projectStepId(),
            null, // ip 없음
            result.authorId(),
            result.authorName(),
            result.title(),
            null, // content 없음
            result.type(),
            result.priority(),
            result.createdAt(),
            null, null
        );
    }

}