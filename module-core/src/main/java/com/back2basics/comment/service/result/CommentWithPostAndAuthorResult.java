package com.back2basics.comment.service.result;

import java.time.LocalDateTime;

public record CommentWithPostAndAuthorResult(
    Long commentId,
    Long postId,
    Long parentId,
    Long authorId,
    String authorName,
    String authorIp,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}