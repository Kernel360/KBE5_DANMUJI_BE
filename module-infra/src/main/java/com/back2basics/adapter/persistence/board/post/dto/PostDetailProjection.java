package com.back2basics.adapter.persistence.board.post.dto;

import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import java.time.LocalDateTime;

public record PostDetailProjection(
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
    PostPriority priority,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}