package com.back2basics.question.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Question {

    private Long id;
    private Long postId;
    private Long authorId;
    private String content;
    private QuestionStatus status;
    private LocalDateTime createdAt;
    private boolean isDeleted;

    @Builder
    public Question(Long id, Long postId, Long authorId, String content, LocalDateTime createdAt,
        boolean isDeleted) {
        this.id = id;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.status = status != null ? status : QuestionStatus.WAITING;
        this.isDeleted = false;
    }

    public void updateContent(String newContent) {
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
