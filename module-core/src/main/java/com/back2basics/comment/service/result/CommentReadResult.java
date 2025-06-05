package com.back2basics.comment.service.result;

import com.back2basics.comment.model.Comment;
import com.back2basics.user.service.result.UserSummaryResult;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentReadResult {

    private Long id;
    private Long postId;
    private Long parentCommentId;
    private UserSummaryResult author;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentReadResult toResult(Comment comment) {
        return CommentReadResult.builder()
            .id(comment.getId())
            .postId(comment.getPostId())
            .parentCommentId(comment.getParentCommentId())
            .author(UserSummaryResult.from(comment.getAuthor()))
            .content(comment.getContent())
            .createdAt(comment.getCreatedAt())
            .updatedAt(comment.getUpdatedAt())
            .build();
    }
}
