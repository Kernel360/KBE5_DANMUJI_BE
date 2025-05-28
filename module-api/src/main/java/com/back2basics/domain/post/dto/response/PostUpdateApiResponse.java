package com.back2basics.domain.post.dto.response;

import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.service.result.PostUpdateResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdateApiResponse {

    private final Long postId;
    private final Long requesterId;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostStatus status;
    private final Integer priority;
    private final LocalDateTime updatedAt;

    public static PostUpdateApiResponse toResponse(PostUpdateResult result) {
        return PostUpdateApiResponse.builder()
            .postId(result.getPostId())
            .requesterId(result.getRequesterId())
            .title(result.getTitle())
            .content(result.getContent())
            .type(result.getType())
            .status(result.getStatus())
            .priority(result.getPriority())
            .updatedAt(result.getUpdatedAt())
            .build();
    }
}
