package com.back2basics.domain.answer.dto.response;

import com.back2basics.answer.service.result.ReadAnswerResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadAnswerResponse {

    private Long id;
    private Long inquiryId;
    private String authorName;
    private String content;
    private LocalDateTime createdAt;

    public static ReadAnswerResponse toResponse(ReadAnswerResult result) {
        return ReadAnswerResponse.builder()
            .id(result.getId())
            .inquiryId(result.getInquiryId())
            .authorName(result.getAuthorName())
            .content(result.getContent())
            .createdAt(result.getCreatedAt())
            .build();
    }

    public static List<ReadAnswerResponse> toResponse(List<ReadAnswerResult> result) {
        return result.stream()
            .map(ReadAnswerResponse::toResponse)
            .collect(Collectors.toList());
    }
}
