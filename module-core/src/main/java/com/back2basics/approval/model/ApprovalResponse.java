package com.back2basics.approval.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalResponse {

    private final Long id;

    private final Long approverId;

    private final Long projectStepId;

    private String message;

    private ApprovalResponseStatus status;

    private LocalDateTime respondedAt;

    public ApprovalResponse(Long id, Long approverId, Long projectStepId, String message,
        ApprovalResponseStatus status, LocalDateTime respondedAt) {
        this.id = id;
        this.approverId = approverId;
        this.projectStepId = projectStepId;
        this.message = message;
        this.status = status;
        this.respondedAt = respondedAt;
    }

    public static ApprovalResponse create(Long userId, Long projectStepId) {
        return new ApprovalResponse(null, userId, projectStepId, null,
            ApprovalResponseStatus.PENDING, null);
    }

    public void updateStatus(String message, ApprovalResponseStatus status) {
        this.status = status;
        this.message = message;
        this.respondedAt = LocalDateTime.now();
    }
}
