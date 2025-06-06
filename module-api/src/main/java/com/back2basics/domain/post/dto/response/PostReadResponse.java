package com.back2basics.domain.post.dto.response;

import com.back2basics.domain.user.dto.response.UserSummaryResponse;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.service.result.PostReadResult;
import com.back2basics.project.service.result.ProjectGetResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReadResponse {

    private final Long postId;
    private final Long parentId;
    private final String authorIp;
    private final UserSummaryResponse author;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostStatus status;
    private final Integer priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime completedAt;
    private final boolean isDelete;
    private final ProjectGetResult project;

    public static PostReadResponse toResponse(PostReadResult postDetails) {
        return PostReadResponse.builder()
            .parentId(postDetails.getParentId())
            .postId(postDetails.getId())
            .authorIp(postDetails.getAuthorIp())
            .author(UserSummaryResponse.from(postDetails.getAuthor()))
            .project(postDetails.getProject())
            .title(postDetails.getTitle())
            .content(postDetails.getContent())
            .type(postDetails.getType())
            .status(postDetails.getStatus())
            .priority(postDetails.getPriority())
            .createdAt(postDetails.getCreatedAt())
            .updatedAt(postDetails.getUpdatedAt())
            .deletedAt(postDetails.getDeletedAt())
            .completedAt(postDetails.getCompletedAt())
            .isDelete(postDetails.isDeleted())
            .build();
    }

}
