package com.back2basics.post.service.result;

import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostPriority;
import com.back2basics.post.model.PostType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReadResult {

    private final Long id;
    private final Long parentId;
    private final Long projectId;
    private final Long projectStepId;
    private final String authorIp;
    private final Long authorId;
    private final String authorName;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostPriority priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime completedAt;
    private final boolean isDeleted;

    public static PostReadResult toResult(Post post) {
        return PostReadResult.builder()
            .id(post.getId())
            .parentId(post.getParentId())
            .authorIp(post.getAuthorIp())
            .authorId(post.getAuthorId())
            .authorName(post.getAuthorName())
            .projectId(post.getProjectId())
            .projectStepId(post.getProjectStepId())
            .title(post.getTitle())
            .content(post.getContent())
            .type(post.getType())
            .priority(post.getPriority())
            .deletedAt(post.getDeletedAt())
            .completedAt(post.getCompletedAt())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .isDeleted(post.getDeletedAt() != null)
            .build();
    }
}
