package com.back2basics.answer.service.result;

import com.back2basics.answer.model.Answer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AnswerReadResult {

    private Long id;
    private Long questionId;
    private Long parentAnswerId;
    private final Long authorId;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AnswerReadResult> children;

    public static AnswerReadResult toResult(Answer answer) {
        return AnswerReadResult.builder()
            .id(answer.getId())
            .questionId(answer.getQuestionId())
            .parentAnswerId(answer.getParentAnswerId())
            .authorId(answer.getAuthorId())
            .content(answer.getContent())
            .createdAt(answer.getCreatedAt())
            .updatedAt(answer.getUpdatedAt())
            .children(answer.getChildren().stream()
                .map(AnswerReadResult::toResult)
                .collect(Collectors.toList()))
            .build();
    }
}
