package com.back2basics.adapter.persistence.comment.projection;

import java.time.LocalDateTime;

public record CommentWithPostAndAuthorResult(
    Long commentId,
    Long parentId,
    Long postId,
    Long authorId,
    String authorIp,
    String authorName,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
