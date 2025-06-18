package com.back2basics.inquiry.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Inquiry {

    private final Long id;
    private Long authorId;
    private String title;
    private String content;
    private InquiryStatus inquiryStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private boolean isDelete;

    @Builder
    public Inquiry(Long id, Long authorId, String title, String content,
        InquiryStatus inquiryStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.inquiryStatus = inquiryStatus != null ? inquiryStatus : InquiryStatus.WAITING;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDelete = false;
    }

}
