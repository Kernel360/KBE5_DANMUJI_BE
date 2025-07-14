package com.back2basics.checklist.service.result;

import com.back2basics.checklist.model.ApprovalStatus;
import java.time.LocalDateTime;

public record ApprovalResult(
    Long id,
    Long checklistId,
    Long userId,
    String name,
    String username,
    String message,
    ApprovalStatus status,
    LocalDateTime createdAt,
    LocalDateTime respondedAt
) {

}
