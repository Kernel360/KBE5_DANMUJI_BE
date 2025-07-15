package com.back2basics.domain.inquiry.dto.response;

import com.back2basics.inquiry.model.InquiryStatus;
import com.back2basics.inquiry.service.result.InquirySummaryResult;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InquirySummaryResponse {

    private final Long inquiryId;
    private final Long authorId;
    private final String name;
    private final String username;
    private final Role role;
    private final String title;
    private final InquiryStatus status;
    private final LocalDateTime createdAt;

    public static InquirySummaryResponse toResponse(InquirySummaryResult result) {
        return InquirySummaryResponse.builder()
            .inquiryId(result.getId())
            .authorId(result.getAuthorId())
            .name(result.getName())
            .username(result.getUsername())
            .role(result.getRole())
            .title(result.getTitle())
            .status(result.getStatus())
            .createdAt(result.getCreatedAt())
            .build();
    }
}