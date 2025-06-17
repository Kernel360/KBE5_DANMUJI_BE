package com.back2basics.post.service.result;

import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostPriority;
import com.back2basics.post.model.PostType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSummaryReadResult {

    private final Long id;
    private final Long projectId;
    private final Long projectStepId;
    private final Long authorId;
    private final String authorName;
    private final String title;
    private final PostType type;
    private final PostPriority priority;
    private final LocalDateTime createdAt;

    public static PostSummaryReadResult toResult(Post post) {
        return PostSummaryReadResult.builder()
            .id(post.getId())
            .projectId(post.getProjectId())
            .projectStepId(post.getProjectStepId())
            .authorId(post.getAuthorId())
            .authorName(post.getAuthorName())
            .title(post.getTitle())
            .type(post.getType())
            .priority(post.getPriority())
            .createdAt(post.getCreatedAt())
            .build();
    }
}