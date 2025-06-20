package com.back2basics.approval.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalUser {

    private final Long id;

    private final Long userId;

    private final Long projectStepId;

    private String message;

    private ApprovalUserStatus status;

    private LocalDateTime respondedAt;

    public ApprovalUser(Long id, Long userId, Long projectStepId, String message,
        ApprovalUserStatus status,
        LocalDateTime respondedAt) {
        this.id = id;
        this.userId = userId;
        this.projectStepId = projectStepId;
        this.message = message;
        this.status = status;
        this.respondedAt = respondedAt;
    }

    public static ApprovalUser create(Long userId, Long projectStepId) {
        return new ApprovalUser(null, userId, projectStepId, null, ApprovalUserStatus.PENDING,
            null);
    }

    public void updateStatus(String message, ApprovalUserStatus status) {
        this.status = status;
        this.message = message;
        this.respondedAt = LocalDateTime.now();
    }
}
