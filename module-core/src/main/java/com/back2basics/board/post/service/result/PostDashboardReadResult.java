package com.back2basics.board.post.service.result;

import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDashboardReadResult {
    private final Long id;
    private final String title;
    private final LocalDateTime createdAt;
    private final String projectName;
    private final String projectStepName;
    private final String authorName;
    private final String authorUsername;
    private final Role authorRole;
    private final PostPriority priority;
    private final PostType type;

    public static PostDashboardReadResult toResult(Post post) {
        return PostDashboardReadResult.builder()
            .id(post.getId())
            .title(post.getTitle())
            .createdAt(post.getCreatedAt())
            .projectName(post.getProjectName())
            .projectStepName(post.getProjectStepName())
            .authorName(post.getAuthorName())
            .authorUsername(post.getAuthorUsername())
            .authorRole(post.getAuthorRole())
            .priority(post.getPriority())
            .type(post.getType())
            .build();
    }
}
