package com.back2basics.adapter.persistence.post.adapter.projection;

import com.back2basics.post.model.PostPriority;
import com.back2basics.post.model.PostType;
import java.time.LocalDateTime;

public record PostSummaryResult(
    Long postId,
    Long parentId,
    Long projectId,
    Long projectStepId,
    Long authorId,
    String authorName,
    String title,
    PostType type,
    PostPriority priority,
    LocalDateTime createdAt
) {

}