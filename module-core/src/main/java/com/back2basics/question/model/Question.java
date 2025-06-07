package com.back2basics.question.model;

import com.back2basics.user.model.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Question {

    private Long id;
    private Long postId;
    private String authorIp;
    private User author;
    private String content;
    private QuestionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private boolean isDeleted;
    private Long answerId;

    @Builder
    public Question(Long id, Long postId, String authorIp, User author, String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt, LocalDateTime deletedAt,
        QuestionStatus status, Long answerId, boolean isDeleted) {
        this.id = id;
        this.postId = postId;
        this.authorIp = authorIp;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.status = status != null ? status : QuestionStatus.WAITING;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDeleted = false;
        this.answerId = answerId;
    }

    public void updateContent(String newContent, String userIp) {
        this.authorIp = userIp;
        this.content = newContent;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    public void markAsAnswered() {
        this.status = QuestionStatus.ANSWERED;
    }

    public void markAsResolved() {
        this.status = QuestionStatus.RESOLVED;
    }

}
