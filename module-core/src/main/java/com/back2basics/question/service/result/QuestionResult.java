package com.back2basics.question.service.result;


import com.back2basics.question.model.Question;
import com.back2basics.question.model.QuestionStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionResult {

    private final Long id;
    private final Long postId;
    private final Long authorId;
    private final String content;
    private final QuestionStatus status;
    private final LocalDateTime createdAt;

    @Builder
    public QuestionResult(Long id, Long postId, Long authorId, String content,
        QuestionStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static QuestionResult toResult(Question question) {
        return QuestionResult.builder()
            .id(question.getId())
            .postId(question.getPostId())
            .authorId(question.getAuthorId())
            .content(question.getContent())
            .status(question.getStatus())
            .createdAt(question.getCreatedAt())
            .build();
    }
}