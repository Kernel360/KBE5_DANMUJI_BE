package com.back2basics.comment.model;

import com.back2basics.comment.port.in.command.CommentUpdateCommand;
import com.back2basics.post.model.Post;
import com.back2basics.user.model.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

    private Long id;
    private Long parentId;
    private Long postId;
    private Long parentCommentId;
    private String authorIp;
    private User author;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Comment(Long id, Long parentId, Long postId, Long parentCommentId, String authorIp,
        User author,
        String content,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.parentId = parentId;
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.authorIp = authorIp;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(CommentUpdateCommand command, String userIp) {
        this.content = command.getContent();
        this.authorIp = userIp;
    }

    public void assignPostId(Post post) {
        this.postId = post.getId();
    }


}
