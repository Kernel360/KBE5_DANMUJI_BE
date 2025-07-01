package com.back2basics.adapter.persistence.board.post.dto;

import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;

public record PostDashboardProjection(
    Long postId,
    String projectName,
    String projectStepName,
    LocalDateTime createdAt,
    String title,
    String authorName,
    String authorUsername,
    Role authorRole,
    PostPriority priority,
    PostType type
) { }

