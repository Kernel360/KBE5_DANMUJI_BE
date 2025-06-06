package com.back2basics.domain.comment.dto.response;

import com.back2basics.comment.service.result.CommentReadResult;
import com.back2basics.domain.user.dto.response.UserSummaryResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReadResponse {

    private final Long id;
    private final Long postId;
    private final Long parentCommentId;
    private final String authorIp;
    private final UserSummaryResponse author;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CommentReadResponse toResponse(CommentReadResult result) {
        return CommentReadResponse.builder()
            .id(result.getId())
            .postId(result.getPostId())
            .parentCommentId(result.getParentCommentId())
            .authorIp(result.getAuthorIp())
            .author(UserSummaryResponse.from(result.getAuthor()))
            .content(result.getContent())
            .createdAt(result.getCreatedAt())
            .updatedAt(result.getUpdatedAt())
            .build();
    }
}
