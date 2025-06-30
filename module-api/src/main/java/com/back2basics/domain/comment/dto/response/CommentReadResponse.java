package com.back2basics.domain.comment.dto.response;

import com.back2basics.comment.service.result.CommentReadResult;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReadResponse {

    private final Long id;
    private final Long postId;
    private final Long parentId;
    private final String authorIp;
    private final Long authorId;
    private final String authorName;
    private final String authorUsername;
    private final Role role;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CommentReadResponse toResponse(CommentReadResult result) {
        return CommentReadResponse.builder()
            .id(result.getId())
            .postId(result.getPostId())
            .parentId(result.getParentId())
            .authorIp(result.getAuthorIp())
            .authorId(result.getAuthorId())
            .authorName(result.getAuthorName())
            .authorUsername(result.getAuthorUsername())
            .role(result.getRole())
            .content(result.getContent())
            .createdAt(result.getCreatedAt())
            .updatedAt(result.getUpdatedAt())
            .build();
    }
}
