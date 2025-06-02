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

    @Builder
    public Question(Long id, Long postId, Long authorId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.status = status != null ? status : QuestionStatus.WAITING;
    }
}
