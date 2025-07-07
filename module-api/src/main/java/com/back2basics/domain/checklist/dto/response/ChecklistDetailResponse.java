package com.back2basics.domain.checklist.dto.response;

import com.back2basics.checklist.model.ChecklistStatus;
import com.back2basics.checklist.service.result.ChecklistDetailResult;
import java.time.LocalDateTime;
import java.util.List;

public record ChecklistDetailResponse(Long id, Long stepId, Long userId, ChecklistStatus status,
                                      LocalDateTime completedAt,
                                      List<ApprovalResponse> approvals) {

    public static ChecklistDetailResponse from(ChecklistDetailResult result) {
        return new ChecklistDetailResponse(
            result.id(),
            result.stepId(),
            result.userId(),
            result.status(),
            result.completedAt(),
            ApprovalResponse.from(result.approvals())
        );
    }
}
