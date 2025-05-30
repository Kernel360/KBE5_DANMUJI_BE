package com.back2basics.post.model;

import com.back2basics.comment.model.Comment;
import com.back2basics.post.port.in.command.PostUpdateCommand;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

    private final Long id;
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
    private List<Comment> comments;
    private boolean isDelete;

    @Builder
    public Post(Long id, Long authorId, String title, String content, PostType type,
        PostStatus status, int priority,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt, LocalDateTime completedAt, List<Comment> comments,
        boolean isDelete) {
        this.id = id;
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
        this.comments = comments != null ? new ArrayList<>(comments) : new ArrayList<>();
        this.isDelete = false;
    }

    public void update(PostUpdateCommand command) {
        this.title = command.getTitle();
        this.content = command.getContent();
        this.type = command.getType();
        this.status = command.getStatus();
        this.priority = command.getPriority();

    }

    public void markDeleted() {
        this.isDelete = true;
    }


    public void addComment(Comment comment) {
        comments.add(comment);
        comment.assignPost(this);
    }
}