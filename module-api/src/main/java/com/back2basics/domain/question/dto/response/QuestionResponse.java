package com.back2basics.domain.question.dto.response;

import com.back2basics.question.model.QuestionStatus;
import com.back2basics.question.service.result.QuestionResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponse {

    private Long id;
    private Long postId;
    private Long authorId;
    private String content;
    private QuestionStatus status;
    private LocalDateTime createdAt;


    public static QuestionResponse toResponse(QuestionResult result) {
        return QuestionResponse.builder()
            .id(result.getId())
            .postId(result.getPostId())
            .authorId(result.getAuthorId())
            .content(result.getContent())
            .status(result.getStatus())
            .createdAt(result.getCreatedAt())
            .build();
    }
}
