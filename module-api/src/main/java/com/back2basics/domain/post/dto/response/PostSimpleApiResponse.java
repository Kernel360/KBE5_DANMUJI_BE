package com.back2basics.domain.post.dto.response;

import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.service.result.PostSimpleResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSimpleApiResponse {

    private final Long id;
    private final Long authorId;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostStatus status;
    private final Integer priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime completedAt;

    private final Integer commentCount;

    public static PostSimpleApiResponse toResponse(PostSimpleResult post) {
        return PostSimpleApiResponse.builder()
            .id(post.getId())
            .authorId(post.getAuthorId())
            .title(post.getTitle())
            .content(post.getContent())
            .type(post.getType())
            .status(post.getStatus())
            .priority(post.getPriority())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .completedAt(post.getCompletedAt())
            .commentCount(post.getCommentCount())
            .build();
    }

}
