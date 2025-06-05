package com.back2basics.post.service.result;

import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.user.service.result.UserSummaryResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReadResult {

    private final Long id;
    private final Long parentId;
    private final UserSummaryResult author;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostStatus status;
    private final Integer priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime completedAt;
    private final boolean isDeleted;
    private final ProjectGetResult project;

    public static PostReadResult toResult(Post post) {
        return PostReadResult.builder()
            .id(post.getId())
            .parentId(post.getParentId())
            .author(UserSummaryResult.from(post.getAuthor()))
            .project(ProjectGetResult.toResult(post.getProject()))
            .title(post.getTitle())
            .content(post.getContent())
            .type(post.getType())
            .status(post.getStatus())
            .priority(post.getPriority())
            .deletedAt(post.getDeletedAt())
            .completedAt(post.getCompletedAt())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .isDeleted(post.getDeletedAt() != null)
            .build();
    }
}
