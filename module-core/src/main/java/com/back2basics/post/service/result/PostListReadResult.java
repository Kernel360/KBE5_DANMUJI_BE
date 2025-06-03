package com.back2basics.post.service.result;

import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListReadResult {

    private final Long id;
    private final Long authorId;
    private final String title;
    private final PostType type;
    private final PostStatus status;
    private final Integer priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;
    private final LocalDateTime completedAt;

    public static PostListReadResult toResult(Post post) {
        return PostListReadResult.builder()
            .id(post.getId())
            .authorId(post.getAuthorId())
            .title(post.getTitle())
            .type(post.getType())
            .status(post.getStatus())
            .priority(post.getPriority())
            .deletedAt(post.getDeletedAt())
            .completedAt(post.getCompletedAt())
            .createdAt(post.getCreatedAt())
            .build();
    }
}
