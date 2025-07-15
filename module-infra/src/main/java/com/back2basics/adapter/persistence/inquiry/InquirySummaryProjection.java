package com.back2basics.adapter.persistence.inquiry;

import com.back2basics.inquiry.model.InquiryStatus;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;

public record InquirySummaryProjection(
    Long inquiryId,
    Long authorId,
    String authorName,
    String authorUsername,
    Role authorRole,
    String title,
    InquiryStatus status,
    LocalDateTime createdAt
) {
}
