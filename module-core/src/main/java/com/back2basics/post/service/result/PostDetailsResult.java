package com.back2basics.post.service.result;

import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.service.comment.dto.CommentResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailsResult {

    private final Long id;
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
    private final boolean isDeleted;

    private final List<CommentResponseDto> comments;

    public static PostDetailsResult toResult(Post post) {
        return PostDetailsResult.builder()
            .id(post.getId())
            .authorId(post.getAuthorId())
            .title(post.getTitle())
            .content(post.getContent())
            .type(post.getType())
            .status(post.getStatus())
            .priority(post.getPriority())
            .deletedAt(post.getDeletedAt())
            .completedAt(post.getCompletedAt())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .comments(post.getComments().stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList()))
            .isDeleted(post.getDeletedAt() != null)
            .build();
    }
}
