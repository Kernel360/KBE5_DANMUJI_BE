package com.back2basics.domain.post.dto.response;

import com.back2basics.comment.service.result.CommentReadResult;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.service.result.PostReadResult;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.user.service.result.UserInfoResult;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReadResponse {

    private final Long postId;
    private final UserInfoResult author;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostStatus status;
    private final Integer priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime completedAt;
    private final List<CommentReadResult> comments;
    private final boolean isDelete;
    private final ProjectGetResult project;

    public static PostReadResponse toResponse(PostReadResult postDetails) {
        return PostReadResponse.builder()
            .postId(postDetails.getId())
            .author(postDetails.getAuthor())
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
            .comments(postDetails.getComments())
            .isDelete(postDetails.isDeleted())
            .build();
    }

}
