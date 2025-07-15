package com.back2basics.inquiry.service.result;

import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.model.InquiryStatus;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InquirySummaryResult {

    private final Long id;
    private final Long authorId;
    private final String name;
    private final String username;
    private final Role role;
    private final String title;
    private final InquiryStatus status;
    private final LocalDateTime createdAt;

    public static InquirySummaryResult toResult(Inquiry inquiry) {
        return InquirySummaryResult.builder()
            .id(inquiry.getId())
            .authorId(inquiry.getAuthorId())
            .name(inquiry.getName())
            .username(inquiry.getUsername())
            .role(inquiry.getRole())
            .title(inquiry.getTitle())
            .status(inquiry.getInquiryStatus())
            .createdAt(inquiry.getCreatedAt())
            .build();
    }

}
