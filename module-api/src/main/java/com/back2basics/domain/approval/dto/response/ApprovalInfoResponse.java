package com.back2basics.domain.approval.dto.response;

import com.back2basics.approval.model.ApprovalRequestStatus;
import com.back2basics.approval.service.result.ApprovalInfoResult;
import java.time.LocalDateTime;
import java.util.List;

public record ApprovalInfoResponse(Long id, Long stepId, Long userId, ApprovalRequestStatus status,
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
