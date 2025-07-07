package com.back2basics.inquiry.service.result;

import com.back2basics.inquiry.model.Inquiry;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadRecentInquiryResult {

    private final Long id;
    private final String title;
    private final LocalDateTime createdAt;

    @Builder
    public ReadRecentInquiryResult(Long id, String title, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
    }

    public static ReadRecentInquiryResult toResult(Inquiry inquiry) {
        return ReadRecentInquiryResult.builder()
            .id(inquiry.getId())
            .title(inquiry.getTitle())
            .createdAt(inquiry.getCreatedAt())
            .build();
    }

}
