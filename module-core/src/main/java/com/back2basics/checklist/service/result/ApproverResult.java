package com.back2basics.checklist.service.result;

import com.back2basics.checklist.model.ApprovalResponseStatus;
import java.time.LocalDateTime;

public record ApproverResult(
    Long id,
    Long approvalRequestId,
    Long userId,
    String message,
    ApprovalResponseStatus status,
    LocalDateTime respondedAt
) {

}
