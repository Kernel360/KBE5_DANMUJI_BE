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
    private Long parentCommentId; // 대댓글의 경우 부모 댓글 ID
    private User author;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Comment(Long id, Long parentId, Long postId, Long parentCommentId, User author,
        String content,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.parentId = parentId;
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(CommentUpdateCommand command) {
        this.content = command.getContent();

    }

    public void assignPostId(Post post) {
        this.postId = post.getId();
    }


}
