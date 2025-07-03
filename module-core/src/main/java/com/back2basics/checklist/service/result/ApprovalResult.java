package com.back2basics.checklist.service.result;

import com.back2basics.checklist.model.ApprovalStatus;
import java.time.LocalDateTime;

public record ApprovalResult(
    Long id,
    Long approvalRequestId,
    Long userId,
    String message,
    ApprovalStatus status,
    LocalDateTime respondedAt
) {

}
