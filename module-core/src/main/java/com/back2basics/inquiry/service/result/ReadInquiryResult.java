package com.back2basics.inquiry.service.result;

import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.model.InquiryStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadInquiryResult {

    private final Long id;
    private final String authorName;
    private final String title;
    private final String content;
    private final InquiryStatus inquiryStatus;
    private final LocalDateTime createdAt;

    @Builder
    public ReadInquiryResult(Long id, String authorName, String title, String content,
        InquiryStatus inquiryStatus, LocalDateTime createdAt) {
        this.id = id;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.inquiryStatus = inquiryStatus;
        this.createdAt = createdAt;
    }

    public static ReadInquiryResult toResult(Inquiry inquiry, String authorName) {
        return ReadInquiryResult.builder()
            .id(inquiry.getId())
            .authorName(authorName)
            .title(inquiry.getTitle())
            .content(inquiry.getContent())
            .inquiryStatus(inquiry.getInquiryStatus())
            .createdAt(inquiry.getCreatedAt())
            .build();
    }

}
