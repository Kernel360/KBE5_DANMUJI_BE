package com.back2basics.checklist.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Approval {

    private final Long id;

    private final Long userId;

    private final Long checklistId;

    private String message;

    private ApprovalStatus status;

    private final LocalDateTime createdAt;

    private LocalDateTime respondedAt;

    private Boolean deleted = false;

    public Approval(Long id, Long userId, Long checklistId, String message,
        ApprovalStatus status, LocalDateTime createdAt, LocalDateTime respondedAt) {
        this.id = id;
        this.userId = userId;
        this.checklistId = checklistId;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
        this.respondedAt = respondedAt;
    }

    public static Approval create(Long userId, Long checklistId) {
        return new Approval(null, userId, checklistId, null,
            ApprovalStatus.PENDING, LocalDateTime.now(), null);
    }

    public void updateStatus(String message, ApprovalStatus status) {
        this.status = status;
        this.message = message;
        this.respondedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deleted = true;
    }

}
