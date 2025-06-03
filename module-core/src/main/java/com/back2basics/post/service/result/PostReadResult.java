package com.back2basics.post.service.result;

import com.back2basics.comment.service.result.CommentReadResult;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.user.model.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReadResult {

    private final Long id;
    private final User author;
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

    private final List<CommentReadResult> comments;

    public static PostReadResult toResult(Post post) {
        return PostReadResult.builder()
            .id(post.getId())
            .author(post.getAuthor())
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
                .map(CommentReadResult::toResult)
                .collect(Collectors.toList()))
            .isDeleted(post.getDeletedAt() != null)
            .build();
    }
}
