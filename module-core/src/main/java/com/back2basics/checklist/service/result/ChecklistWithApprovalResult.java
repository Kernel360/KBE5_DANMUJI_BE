package com.back2basics.checklist.service.result;

import com.back2basics.checklist.model.ApprovalStatus;
import java.time.LocalDateTime;

public record ChecklistWithApprovalResult(
    Long id,
    Long checklistId,
    Long userId,
    String message,
    ApprovalStatus status,
    LocalDateTime respondedAt
) {

}
