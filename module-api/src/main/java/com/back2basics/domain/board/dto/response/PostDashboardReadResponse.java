package com.back2basics.domain.board.dto.response;

import com.back2basics.board.post.service.result.PostDashboardReadResult;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDashboardReadResponse {

    private final Long postId;
    private final String title;
    private final LocalDateTime createdAt;
    private final String projectName;
    private final String projectStepName;
    private final String authorName;
    private final String authorUsername;
    private final Role authorRole;

    public static PostDashboardReadResponse toResponse(PostDashboardReadResult result) {
        return PostDashboardReadResponse.builder()
            .postId(result.getId())
            .title(result.getTitle())
            .createdAt(result.getCreatedAt())
            .projectName(result.getProjectName())
            .projectStepName(result.getProjectStepName())
            .authorName(result.getAuthorName())
            .authorUsername(result.getAuthorUsername())
            .authorRole(result.getAuthorRole())
            .build();
    }
}
