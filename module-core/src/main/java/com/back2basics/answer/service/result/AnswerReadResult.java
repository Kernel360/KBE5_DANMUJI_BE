package com.back2basics.answer.service.result;

import com.back2basics.answer.model.Answer;
import com.back2basics.user.service.result.UserSummaryResult;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AnswerReadResult {

    private Long id;
    private Long questionId;
    private Long parentId;
    private String authorIp;
    private UserSummaryResult author;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AnswerReadResult toResult(Answer answer) {
        return AnswerReadResult.builder()
            .id(answer.getId())
            .questionId(answer.getQuestionId())
            .parentId(answer.getParentId())
            .authorIp(answer.getAuthorIp())
            .author(UserSummaryResult.from(answer.getAuthor()))
            .content(answer.getContent())
            .createdAt(answer.getCreatedAt())
            .updatedAt(answer.getUpdatedAt())
            .build();
    }
}
