package com.back2basics.inquiry.service.result;

import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.model.InquiryStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadInquiryResult {

    private final Long id;
    private final String authorName;
    private final String title;
    private final String content;
    private final InquiryStatus inquiryStatus;

    @Builder
    public ReadInquiryResult(Long id, String authorName, String title, String content,
        InquiryStatus inquiryStatus) {
        this.id = id;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.inquiryStatus = inquiryStatus;
    }

    public static ReadInquiryResult toResult(Inquiry inquiry, String authorName) {
        return ReadInquiryResult.builder()
            .id(inquiry.getId())
            .authorName(authorName)
            .title(inquiry.getTitle())
            .content(inquiry.getContent())
            .inquiryStatus(inquiry.getInquiryStatus())
            .build();
    }

}
