package com.back2basics.board.post.service.result;

import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSummaryReadResult {

    private final Long id;
    private final Long parentId;
    private final Long projectId;
    private final Long projectStepId;
    private final Long authorId;
    private final String authorName;
    private final String authorUsername;
    private final String title;
    private final PostType type;
    private final PostPriority priority;
    private final LocalDateTime createdAt;
    private final Long commentCount;

    public static PostSummaryReadResult toResult(Post post) {
        return PostSummaryReadResult.builder()
            .id(post.getId())
            .parentId(post.getParentId())
            .projectId(post.getProjectId())
            .projectStepId(post.getProjectStepId())
            .authorId(post.getAuthorId())
            .authorName(post.getAuthorName())
            .authorUsername(post.getAuthorUsername())
            .title(post.getTitle())
            .type(post.getType())
            .priority(post.getPriority())
            .createdAt(post.getCreatedAt())
            .commentCount(post.getCommentCount())
            .build();
    }
}