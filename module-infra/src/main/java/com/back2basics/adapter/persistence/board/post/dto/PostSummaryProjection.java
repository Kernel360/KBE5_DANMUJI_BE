package com.back2basics.adapter.persistence.board.post.dto;

import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;

public record PostSummaryProjection(
    Long postId,
    Long parentId,
    Long projectId,
    Long projectStepId,
    Long authorId,
    String authorName,
    String authorUsername,
    Role authorRole,
    String title,
    PostType type,
    PostPriority priority,
    LocalDateTime createdAt
) {

}