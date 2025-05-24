package com.back2basics.model.post;

import com.back2basics.service.post.dto.PostUpdateCommand;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

    private final Long id;
    private String authorName;
    private String title;
    private String content;
    private PostType type;
    private PostStatus status;
    private int priority;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime completedAt;

    @Builder
    public Post(Long id, String authorName, String title, String content, PostType type,
        PostStatus status, int priority,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt, LocalDateTime completedAt) {
        this.id = id;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.type = type;
        this.status = status != null ? status : PostStatus.PENDING;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.completedAt = completedAt;
    }

    public void update(PostUpdateCommand command) {
        if (command.getTitle() != null) {
            this.title = title;
        }
        if (command.getContent() != null) {
            this.content = content;
        }
        if (command.getType() != null) {
            this.type = type;
        }
        if (command.getStatus() != null) {
            this.status = status;
        }
        if (command.getPriority() != null) {
            this.priority = priority;
        }
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}