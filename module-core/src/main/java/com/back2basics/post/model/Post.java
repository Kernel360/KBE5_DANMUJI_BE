package com.back2basics.post.model;

import com.back2basics.comment.model.Comment;
import com.back2basics.post.port.in.command.PostUpdateCommand;
import com.back2basics.project.model.Project;
import com.back2basics.user.model.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

    private final Long id;
    private final Long parentId;
    private final Long projectId;
    private final Long projectStepId;
    private String authorIp;
    private User author;
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


    @Builder
    public Post(Long id, Long parentId, Long projectId, Long projectStepId, String authorIp,
        User author,
        String title, String content,
        PostType type,
        PostStatus status, int priority,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt, LocalDateTime completedAt, List<Comment> comments,
        Project project) {
        this.id = id;
        this.parentId = parentId;
        this.authorIp = authorIp;
        this.author = author;
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
        this.projectId = projectId;
        this.projectStepId = projectStepId;
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
}