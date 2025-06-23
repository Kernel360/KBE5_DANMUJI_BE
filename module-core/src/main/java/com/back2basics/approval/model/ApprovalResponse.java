package com.back2basics.approval.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalResponse {

    private final Long id;

    private final Long userId;

    private final Long approvalRequestId;

    private String message;

    private ApprovalResponseStatus status;

    private LocalDateTime respondedAt;

    public ApprovalResponse(Long id, Long userId, Long approvalRequestId, String message,
        ApprovalResponseStatus status, LocalDateTime respondedAt) {
        this.id = id;
        this.userId = userId;
        this.approvalRequestId = approvalRequestId;
        this.message = message;
        this.status = status;
        this.respondedAt = respondedAt;
    }

    public static ApprovalResponse create(Long userId, Long approvalRequestId) {
        return new ApprovalResponse(null, userId, approvalRequestId, null,
            ApprovalResponseStatus.PENDING, null);
    }

    public void updateStatus(String message, ApprovalResponseStatus status) {
        this.status = status;
        this.message = message;
        this.respondedAt = LocalDateTime.now();
    }
}
