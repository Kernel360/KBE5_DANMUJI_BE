package com.back2basics.domain.answer.dto.response;

import com.back2basics.answer.service.result.AnswerReadResult;
import com.back2basics.domain.user.dto.response.UserSummaryResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerResponse {

    private Long id;
    private Long questionId;
    private Long parentAnswerId;
    private UserSummaryResponse author;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AnswerResponse> children;

    public static AnswerResponse toResponse(AnswerReadResult result) {

        List<AnswerResponse> childrenResponses = result.getChildren().stream()
            .map(AnswerResponse::toResponse)
            .collect(Collectors.toList());

        return AnswerResponse.builder()
            .id(result.getId())
            .questionId(result.getQuestionId())
            .parentAnswerId(result.getParentAnswerId())
            .author(UserSummaryResponse.from(result.getAuthor()))
            .content(result.getContent())
            .createdAt(result.getCreatedAt())
            .updatedAt(result.getUpdatedAt())
            .children(childrenResponses)
            .build();
    }
}
