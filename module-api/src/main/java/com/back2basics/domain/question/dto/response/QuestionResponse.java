package com.back2basics.domain.question.dto.response;

import com.back2basics.question.model.QuestionStatus;
import com.back2basics.question.service.result.QuestionResult;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponse {

    private Long id;
    private Long postId;
    private Long authorId;
    private String content;
    private QuestionStatus status;
    private LocalDateTime createdAt;

    public QuestionResponse(QuestionResult result) {
        this.id = result.getId();
        this.postId = result.getPostId();
        this.authorId = result.getAuthorId();
        this.content = result.getContent();
        this.status = result.getStatus();
        this.createdAt = result.getCreatedAt();
    }
}
