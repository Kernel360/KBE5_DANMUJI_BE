package com.back2basics.domain.post.dto.response;

import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.service.result.PostListReadResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListReadResponse {

    private final Long postId;
    private final Long authorId;
    private final String title;
    private final PostType type;
    private final PostStatus status;
    private final Integer priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime completedAt;

    public static PostListReadResponse toResponse(PostListReadResult posts) {
        return PostListReadResponse.builder()
            .postId(posts.getId())
            .authorId(posts.getAuthorId())
            .title(posts.getTitle())
            .type(posts.getType())
            .status(posts.getStatus())
            .priority(posts.getPriority())
            .createdAt(posts.getCreatedAt())
            .deletedAt(posts.getDeletedAt())
            .completedAt(posts.getCompletedAt())
            .build();
    }

}