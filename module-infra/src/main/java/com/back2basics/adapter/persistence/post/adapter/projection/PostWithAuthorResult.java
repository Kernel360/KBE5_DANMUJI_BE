package com.back2basics.adapter.persistence.post.adapter.projection;

import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import java.time.LocalDateTime;

public record PostWithAuthorResult(
    Long postId,
    Long parentId,
    Long projectId,
    Long projectStepId,
    String authorIp,
    Long authorId,
    String authorName,
    String title,
    String content,
    PostType type,
    PostStatus status,
    Integer priority,
    LocalDateTime createdAt,
    LocalDateTime completedAt
) {

}