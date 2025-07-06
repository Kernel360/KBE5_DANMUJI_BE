package com.back2basics.domain.inquiry.dto.response;

import com.back2basics.inquiry.service.result.ReadRecentInquiryResult;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadRecentInquiryResponse {

    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public static ReadRecentInquiryResponse toResponse(ReadRecentInquiryResult result) {
        return ReadRecentInquiryResponse.builder()
            .id(result.getId())
            .title(result.getTitle())
            .createdAt(result.getCreatedAt())
            .build();

    }
}
