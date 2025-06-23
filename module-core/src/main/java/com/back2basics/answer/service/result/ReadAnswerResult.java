package com.back2basics.answer.service.result;

import com.back2basics.answer.model.Answer;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReadAnswerResult {

    private Long id;
    private Long inquiryId;
    private String authorName;
    private String content;
    private final LocalDateTime createdAt;

    public static ReadAnswerResult toResult(Answer answer, String authorName) {
        return ReadAnswerResult.builder()
            .id(answer.getId())
            .inquiryId(answer.getInquiryId())
            .authorName(authorName)
            .content(answer.getContent())
            .createdAt(answer.getCreatedAt())
            .build();
    }
}
