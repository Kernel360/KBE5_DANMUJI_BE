package com.back2basics.post.model;

import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.port.in.command.PostUpdateCommand;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Post {

    private final Long id;
    private final Long parentId;
    private final Long projectId;
    private final Long projectStepId;
    private String authorIp;
    private Long authorId;
    private String title;
    private String content;
    private PostType type;
    private PostStatus status;
    private int priority;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime completedAt;
    private boolean isDelete;


    public static Post create(PostCreateCommand command, Long userId, String userIp) {
        return new Post(
            null, // id는 생성 시점에는 null
            command.getParentId(),
            command.getProjectId(),
            command.getStepId(),
            userIp,
            userId,
            command.getTitle(),
            command.getContent(),
            command.getType(),
            command.getStatus(),
            command.getPriority(),
            null, null, null, null
        );
    }

    public static Post create(
        Long id, Long parentId, Long projectId, Long projectStepId,
        String authorIp, Long authorId, String title, String content,
        PostType type, PostStatus status, int priority,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt, LocalDateTime completedAt
    ) {
        return new Post(id, parentId, projectId, projectStepId, authorIp, authorId,
            title, content, type, status, priority,
            createdAt, updatedAt, deletedAt, completedAt);
    }

    public void update(PostUpdateCommand command, String userIp) {
        this.title = command.getTitle();
        this.content = command.getContent();
        this.type = command.getType();
        this.status = command.getStatus();
        this.priority = command.getPriority();
        this.authorIp = userIp;
    }

    public void markDeleted() {
        this.isDelete = true;
        this.deletedAt = LocalDateTime.now();
    }

    private Post(Long id, Long parentId, Long projectId, Long projectStepId, String authorIp,
        Long authorId, String title, String content,
        PostType type, PostStatus status, int priority,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt, LocalDateTime completedAt) {
        this.id = id;
        this.parentId = parentId;
        this.projectId = projectId;
        this.projectStepId = projectStepId;
        this.authorIp = authorIp;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.type = type;
        this.status = status != null ? status : PostStatus.PENDING;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.completedAt = completedAt;
        this.isDelete = false;
    }
}