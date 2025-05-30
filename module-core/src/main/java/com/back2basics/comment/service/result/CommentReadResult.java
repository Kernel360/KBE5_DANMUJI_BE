package com.back2basics.comment.service.result;

import com.back2basics.comment.model.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentReadResult {

    private Long id;
    private Long postId;
    private Long parentCommentId; // 대댓글의 경우 부모 댓글 ID
    private final String authorName;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentReadResult> children;

    public static CommentReadResult toResult(Comment comment) {
        return CommentReadResult.builder()
            .id(comment.getId())
            .postId(comment.getPostId())
            .parentCommentId(comment.getParentCommentId())
            .authorName(comment.getAuthorName())
            .content(comment.getContent())
            .createdAt(comment.getCreatedAt())
            .updatedAt(comment.getUpdatedAt())
            .children(comment.getChildren().stream()
                .map(CommentReadResult::toResult)
                .collect(Collectors.toList()))
            .build();
    }
}
