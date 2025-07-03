package com.back2basics.checklist.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Approval {

    private final Long id;

    private final Long userId;

    private final Long approvalRequestId;

    private String message;

    private ApprovalStatus status;

    private LocalDateTime respondedAt;

    public Approval(Long id, Long userId, Long approvalRequestId, String message,
        ApprovalStatus status, LocalDateTime respondedAt) {
        this.id = id;
        this.userId = userId;
        this.approvalRequestId = approvalRequestId;
        this.message = message;
        this.status = status;
        this.respondedAt = respondedAt;
    }

    public static Approval create(Long userId, Long approvalRequestId) {
        return new Approval(null, userId, approvalRequestId, null,
            ApprovalStatus.PENDING, null);
    }

    public void updateStatus(String message, ApprovalStatus status) {
        this.status = status;
        this.message = message;
        this.respondedAt = LocalDateTime.now();
    }
}
