package com.back2basics.domain.post.dto.response;

import com.back2basics.post.model.PostPriority;
import com.back2basics.post.model.PostType;
import com.back2basics.post.service.result.PostReadResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReadResponse {

    private final Long postId;
    private final Long parentId;
    private final Long projectId;
    private final Long projectStepId;
    private final String authorIp;
    private final Long authorId;
    private final String authorName;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostPriority status;
    private final Integer priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime completedAt;
    private final boolean isDelete;

    public static PostReadResponse toResponse(PostReadResult postDetails) {
        return PostReadResponse.builder()
            .parentId(postDetails.getParentId())
            .postId(postDetails.getId())
            .authorIp(postDetails.getAuthorIp())
            .authorId(postDetails.getAuthorId())
            .authorName(postDetails.getAuthorName())
            .projectId(postDetails.getProjectId())
            .projectStepId(postDetails.getProjectStepId())
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
