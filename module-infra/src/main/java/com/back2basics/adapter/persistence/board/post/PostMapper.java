package com.back2basics.adapter.persistence.board.post;

import com.back2basics.adapter.persistence.board.post.dto.PostDashboardProjection;
import com.back2basics.adapter.persistence.board.post.dto.PostDetailProjection;
import com.back2basics.adapter.persistence.board.post.dto.PostSummaryProjection;
import com.back2basics.board.file.model.File;
import com.back2basics.board.link.model.Link;
import com.back2basics.board.post.model.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    public Post toDomain(PostEntity entity) {
        return Post.createWithoutFilesAndLinks(
            entity.getId(),
            entity.getParentId(),
            entity.getProjectId(),
            entity.getProjectStepId(),
            entity.getAuthorIp(),
            entity.getAuthorId(),
            null,
            null,
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

    public Post toDomain(PostDetailProjection result) {
        return Post.createWithoutFilesAndLinks(
            result.postId(),
            result.parentId(),
            result.projectId(),
            result.projectStepId(),
            result.authorIp(),
            result.authorId(),
            result.authorName(),
            result.authorUsername(),
            result.authorRole(),
            result.title(),
            result.content(),
            result.type(),
            result.priority(),
            result.createdAt(),
            result.updatedAt(),
            null
        );
    }

    public Post toDomain(PostSummaryProjection result) {
        return Post.createSummaryPost(
            result.postId(),
            result.parentId(),
            result.projectId(),
            result.projectStepId(),
            null, // ip 없음
            result.authorId(),
            result.authorName(),
            result.authorUsername(),
            result.authorRole(),
            result.title(),
            null, // content 없음
            result.type(),
            result.priority(),
            result.createdAt(),
            null,
            null,
            result.commentCount()
        );
    }

    public Post toDomain(PostDetailProjection result, List<File> files, List<Link> links) {
        return Post.createWithFilesAndLinks(
            result.postId(),
            result.parentId(),
            result.projectId(),
            result.projectStepId(),
            result.authorIp(),
            result.authorId(),
            result.authorName(),
            result.authorUsername(),
            result.authorRole(),
            result.title(),
            result.content(),
            result.type(),
            result.priority(),
            result.createdAt(),
            result.updatedAt(),
            null,
            files,
            links
        );
    }

    public Post toDomain(PostDashboardProjection projection) {
        return Post.createDashboardPost(
            projection.postId(),
            projection.title(),
            projection.createdAt(),
            projection.projectName(),
            projection.projectStepName(),
            projection.authorName(),
            projection.authorUsername(),
            projection.authorRole(),
            projection.priority(),
            projection.type()
        );
    }

}