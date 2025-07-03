package com.back2basics.domain.checklist.dto.response;

import com.back2basics.checklist.model.ChecklistStatus;
import com.back2basics.checklist.service.result.ApprovalInfoResult;
import java.time.LocalDateTime;
import java.util.List;

public record ApprovalInfoResponse(Long id, Long stepId, Long userId, ChecklistStatus status,
                                   LocalDateTime completedAt) {

    public static ApprovalInfoResponse from(ApprovalInfoResult result) {
        return new ApprovalInfoResponse(
            result.id(),
            result.stepId(),
            result.userId(),
            result.status(),
            result.completedAt()
        );
    }

    public static List<ApprovalInfoResponse> from(List<ApprovalInfoResult> results) {
        return results.stream()
            .map(ApprovalInfoResponse::from)
            .toList();
    }
}
