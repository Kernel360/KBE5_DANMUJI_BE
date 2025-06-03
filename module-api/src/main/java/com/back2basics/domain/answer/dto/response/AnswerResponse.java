package com.back2basics.domain.answer.dto.response;

import com.back2basics.answer.service.result.AnswerReadResult;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerResponse {

    private Long id;
    private Long questionId;
    private Long parentAnswerId;
    private final Long authorId;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AnswerReadResult> children;

    public static AnswerResponse toResponse(AnswerReadResult result) {
        return AnswerResponse.builder()
            .id(result.getId())
            .questionId(result.getQuestionId())
            .parentAnswerId(result.getParentAnswerId())
            .authorId(result.getAuthorId())
            .content(result.getContent())
            .createdAt(result.getCreatedAt())
            .updatedAt(result.getUpdatedAt())
            .children(result.getChildren())
            .build();
    }
}
