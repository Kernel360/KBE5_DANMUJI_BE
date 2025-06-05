package com.back2basics.domain.comment.dto.response;

import com.back2basics.comment.service.result.CommentReadResult;
import com.back2basics.domain.user.dto.response.UserSummaryResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {

    private Long id;
    private Long postId;
    private Long parentCommentId; // 대댓글의 경우 부모 댓글 ID
    private UserSummaryResponse author;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponse toResponse(CommentReadResult result) {
        return CommentResponse.builder()
            .id(result.getId())
            .postId(result.getPostId())
            .parentCommentId(result.getParentCommentId())
            .author(UserSummaryResponse.from(result.getAuthor()))
            .content(result.getContent())
            .createdAt(result.getCreatedAt())
            .updatedAt(result.getUpdatedAt())
            .build();
    }
}
