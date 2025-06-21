package com.back2basics.approval.service.result;

import com.back2basics.approval.model.ApprovalResponseStatus;
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
