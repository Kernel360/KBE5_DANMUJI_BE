package com.back2basics.comment.model;

import com.back2basics.comment.port.in.command.CommentUpdateCommand;
import com.back2basics.post.model.Post;
import com.back2basics.user.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

    private Long id;
    private Long postId;
    private Long parentCommentId; // 대댓글의 경우 부모 댓글 ID
    private User author;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Comment> children;

    @Builder
    public Comment(Long id, Long postId, Long parentCommentId, User author,
        String content,
        LocalDateTime createdAt, LocalDateTime updatedAt, List<Comment> children) {
        this.id = id;
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.children = children != null ? children : new ArrayList<>();
    }

    public void update(CommentUpdateCommand command) {
        this.content = command.getContent();

    }

    public void assignPostId(Post post) {
        this.postId = post.getId();
    }

    public void assignParent(Comment parent) {
        this.parentCommentId = parent.getId();
    }

    public void addChild(Comment child) {
        children.add(child);
        child.assignParent(this);
    }

}
