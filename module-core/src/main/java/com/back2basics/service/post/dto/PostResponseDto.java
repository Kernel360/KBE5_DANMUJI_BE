package com.back2basics.service.post.dto;

import com.back2basics.model.post.Post;
import com.back2basics.model.post.PostStatus;
import com.back2basics.model.post.PostType;
import com.back2basics.service.comment.dto.CommentResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDto {

    private final Long id;
    private final String authorName;
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

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
            .id(post.getId())
            .authorName(post.getAuthorName())
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
