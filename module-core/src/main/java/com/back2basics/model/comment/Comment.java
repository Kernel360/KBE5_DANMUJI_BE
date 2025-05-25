package com.back2basics.model.comment;

import com.back2basics.service.comment.dto.CommentUpdateCommand;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

    private final Long id;
    private final Long postId;
    private final Long parentId; // 대댓글의 경우 부모 댓글 ID
    private final String authorName;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Comment(Long id, Long postId, Long parentId, String authorName, String content,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.postId = postId;
        this.parentId = parentId;
        this.authorName = authorName;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(CommentUpdateCommand command) {
        if (command.getContent() != null) {
            this.content = command.getContent();
        }
    }

}
