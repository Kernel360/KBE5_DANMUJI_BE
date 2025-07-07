package com.back2basics.domain.checklist.dto.response;

import com.back2basics.checklist.model.ChecklistStatus;
import com.back2basics.checklist.service.result.ChecklistInfoResult;
import java.time.LocalDateTime;
import java.util.List;

public record ChecklistInfoResponse(Long id, Long stepId, Long userId, ChecklistStatus status,
                                    LocalDateTime completedAt) {

    public static ChecklistInfoResponse from(ChecklistInfoResult result) {
        return new ChecklistInfoResponse(
            result.id(),
            result.stepId(),
            result.userId(),
            result.status(),
            result.completedAt()
        );
    }

    public static List<ChecklistInfoResponse> from(List<ChecklistInfoResult> results) {
        return results.stream()
            .map(ChecklistInfoResponse::from)
            .toList();
    }
}
