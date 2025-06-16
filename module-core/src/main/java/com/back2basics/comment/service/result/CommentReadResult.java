package com.back2basics.comment.service.result;

import com.back2basics.comment.model.Comment;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReadResult {

    private final Long id;
    private final Long postId;
    private final Long parentId;
    private final String authorIp;
    private final Long authorId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CommentReadResult toResult(Comment comment) {
        return CommentReadResult.builder()
            .id(comment.getId())
            .postId(comment.getPostId())
            .parentId(comment.getParentId())
            .authorIp(comment.getAuthorIp())
            .authorId(comment.getAuthorId())
            .content(comment.getContent())
            .createdAt(comment.getCreatedAt())
            .updatedAt(comment.getUpdatedAt())
            .build();
    }
}
