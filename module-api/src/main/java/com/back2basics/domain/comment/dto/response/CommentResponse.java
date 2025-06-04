package com.back2basics.domain.comment.dto.response;

import com.back2basics.comment.service.result.CommentReadResult;
import com.back2basics.user.service.result.UserInfoResult;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {

    private Long id;
    private Long postId;
    private Long parentCommentId; // 대댓글의 경우 부모 댓글 ID
    private UserInfoResult author;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentReadResult> children;

    public static CommentResponse toResponse(CommentReadResult result) {
        return CommentResponse.builder()
            .id(result.getId())
            .postId(result.getPostId())
            .parentCommentId(result.getParentCommentId())
            .author(result.getAuthor())
            .content(result.getContent())
            .createdAt(result.getCreatedAt())
            .updatedAt(result.getUpdatedAt())
            .children(result.getChildren())
            .build();
    }
}
