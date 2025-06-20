package com.back2basics.domain.inquiry.dto.response;

import com.back2basics.inquiry.model.InquiryStatus;
import com.back2basics.inquiry.service.result.ReadInquiryResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadInquiryResponse {

    private Long id;
    private String authorName;
    private String title;
    private String content;
    private InquiryStatus inquiryStatus;
    private LocalDateTime createdAt;

    public static ReadInquiryResponse toResponse(ReadInquiryResult result) {
        return ReadInquiryResponse.builder()
            .id(result.getId())
            .authorName(result.getAuthorName())
            .title(result.getTitle())
            .content(result.getContent())
            .inquiryStatus(result.getInquiryStatus())
            .createdAt(result.getCreatedAt())
            .build();
    }

    public static List<ReadInquiryResponse> toResponse(List<ReadInquiryResult> result) {
        return result.stream()
            .map(ReadInquiryResponse::toResponse)
            .collect(Collectors.toList());
    }

}
