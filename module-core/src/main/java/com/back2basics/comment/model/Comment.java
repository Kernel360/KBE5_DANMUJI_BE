package com.back2basics.comment.model;

import com.back2basics.comment.port.in.command.CommentCreateCommand;
import com.back2basics.comment.port.in.command.CommentUpdateCommand;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Comment {

    private Long id;
    private Long parentId;
    private Long postId;
    private String authorIp;
    private Long authorId;
    private String authorName;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Comment(Long id, Long parentId, Long postId, String authorIp,
        Long authorId, String authorName, String content, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        this.id = id;
        this.parentId = parentId;
        this.postId = postId;
        this.authorIp = authorIp;
        this.authorId = authorId;
        this.authorName = authorName;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Comment create(CommentCreateCommand command, String authorIp, Long authorId) {
        return new Comment(
            null,
            command.getParentId(),
            command.getPostId(),
            authorIp,
            authorId,
            null,
            command.getContent(),
            null,
            null
        );
    }

    public static Comment create(Long id, Long parentId, Long postId,
        String authorIp, Long authorId, String authorName, String content,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Comment(id, parentId, postId, authorIp, authorId, authorName, content,
            createdAt, updatedAt);
    }

    public void update(CommentUpdateCommand command, String userIp) {
        this.content = command.getContent();
        this.authorIp = userIp;
    }


}
