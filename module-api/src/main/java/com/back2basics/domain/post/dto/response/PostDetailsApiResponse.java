package com.back2basics.domain.post.dto.response;

import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.service.result.PostDetailsResult;
import com.back2basics.service.comment.dto.CommentResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailsApiResponse {

    private final Long postId;
    private final Long authorId;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostStatus status;
    private final Integer priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime completedAt;
    private final List<CommentResponseDto> comments;
    private final boolean isDelete;

    public static PostDetailsApiResponse toResponse(PostDetailsResult postDetails) {
        return PostDetailsApiResponse.builder()
            .postId(postDetails.getId())
            .authorId(postDetails.getAuthorId())
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
