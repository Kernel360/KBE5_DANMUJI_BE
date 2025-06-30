package com.back2basics.adapter.persistence.comment.projection;

import com.back2basics.user.model.Role;
import java.time.LocalDateTime;

public record CommentReadProjection(
    Long commentId,
    Long parentId,
    Long postId,
    Long authorId,
    String authorIp,
    String authorName,
    String authorUsername,
    Role authorRole,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
