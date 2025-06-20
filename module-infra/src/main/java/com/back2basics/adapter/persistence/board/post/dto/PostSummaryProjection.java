package com.back2basics.adapter.persistence.board.post.dto;

import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import java.time.LocalDateTime;

public record PostSummaryProjection(
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