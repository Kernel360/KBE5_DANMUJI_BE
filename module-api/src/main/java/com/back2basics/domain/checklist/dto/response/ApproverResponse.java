package com.back2basics.domain.checklist.dto.response;

import com.back2basics.checklist.model.ApprovalStatus;
import com.back2basics.checklist.service.result.ApprovalResult;
import java.time.LocalDateTime;
import java.util.List;

public record ApproverResponse(
    Long id,
    Long approvalRequestId,
    Long userId,
    String message,
    ApprovalStatus status,
    LocalDateTime respondedAt
) {

    public static ApproverResponse from(ApprovalResult result) {
        return new ApproverResponse(
            result.id(),
            result.approvalRequestId(),
            result.userId(),
            result.message(),
            result.status(),
            result.respondedAt()
        );
    }

    public static List<ApproverResponse> from(List<ApprovalResult> results) {
        return results.stream()
            .map(ApproverResponse::from)
            .toList();
    }
}
