package com.back2basics.domain.checklist.dto.response;

import com.back2basics.checklist.model.ApprovalStatus;
import com.back2basics.checklist.service.result.ApprovalResult;
import java.time.LocalDateTime;
import java.util.List;

public record ApprovalResponse(
    Long id,
    Long checklistId,
    Long userId,
    String message,
    ApprovalStatus status,
    LocalDateTime respondedAt
) {

    public static ApprovalResponse from(ApprovalResult result) {
        return new ApprovalResponse(
            result.id(),
            result.checklistId(),
            result.userId(),
            result.message(),
            result.status(),
            result.respondedAt()
        );
    }

    public static List<ApprovalResponse> from(
        List<ApprovalResult> results) {
        return results.stream()
            .map(ApprovalResponse::from)
            .toList();
    }

}
