package com.back2basics.domain.answer.dto.response;

import com.back2basics.answer.service.result.AnswerReadResult;
import com.back2basics.domain.user.dto.response.UserSummaryResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerResponse {

    private Long id;
    private Long questionId;
    private Long parentId;
    private UserSummaryResponse author;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AnswerResponse toResponse(AnswerReadResult result) {

        return AnswerResponse.builder()
            .id(result.getId())
            .questionId(result.getQuestionId())
            .parentId(result.getParentId())
            .author(UserSummaryResponse.from(result.getAuthor()))
            .content(result.getContent())
            .createdAt(result.getCreatedAt())
            .updatedAt(result.getUpdatedAt())
            .build();
    }
}
