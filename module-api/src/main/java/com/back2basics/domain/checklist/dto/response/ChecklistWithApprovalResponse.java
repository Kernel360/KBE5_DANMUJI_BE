package com.back2basics.domain.checklist.dto.response;

import com.back2basics.checklist.model.ApprovalStatus;
import com.back2basics.checklist.service.result.ChecklistWithApprovalResult;
import java.time.LocalDateTime;
import java.util.List;

public record ChecklistWithApprovalResponse(
    Long id,
    Long checklistId,
    Long userId,
    String message,
    ApprovalStatus status,
    LocalDateTime respondedAt
) {

    public static ChecklistWithApprovalResponse from(ChecklistWithApprovalResult result) {
        return new ChecklistWithApprovalResponse(
            result.id(),
            result.checklistId(),
            result.userId(),
            result.message(),
            result.status(),
            result.respondedAt()
        );
    }

    public static List<ChecklistWithApprovalResponse> from(
        List<ChecklistWithApprovalResult> results) {
        return results.stream()
            .map(ChecklistWithApprovalResponse::from)
            .toList();
    }
}
