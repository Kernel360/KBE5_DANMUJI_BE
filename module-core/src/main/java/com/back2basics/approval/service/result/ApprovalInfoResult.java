package com.back2basics.approval.service.result;

import com.back2basics.approval.model.ApprovalRequestStatus;
import java.time.LocalDateTime;

public record ApprovalInfoResult(Long id, Long stepId, Long userId, ApprovalRequestStatus status,
                                 LocalDateTime completedAt) {

}