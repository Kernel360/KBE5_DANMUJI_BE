package com.back2basics.domain.post.dto.response;

import com.back2basics.post.model.PostPriority;
import com.back2basics.post.model.PostType;
import com.back2basics.post.service.result.PostSummaryReadResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSummaryReadResponse {

    private final Long postId;
    private final Long projectId;
    private final Long projectStepId;
    private final Long authorId;
    private final String authorName;
    private final String title;
    private final PostType type;
    private final PostPriority priority;
    private final LocalDateTime createdAt;

    public static PostSummaryReadResponse toResponse(PostSummaryReadResult result) {
        return PostSummaryReadResponse.builder()
            .postId(result.getId())
            .projectId(result.getProjectId())
            .projectStepId(result.getProjectStepId())
            .authorId(result.getAuthorId())
            .authorName(result.getAuthorName())
            .title(result.getTitle())
            .type(result.getType())
            .priority(result.getPriority())
            .createdAt(result.getCreatedAt())
            .build();
    }
}