package com.back2basics.approval.model;

import com.back2basics.projectstep.model.ProjectStep;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalRequest {

    private final Long id;

    private final ProjectStep projectStep;

    private ApprovalRequestStatus approvalRequestStatus;

    private final LocalDateTime requestedAt;

    private LocalDateTime completedAt;

    public ApprovalRequest(Long id, ProjectStep projectStep,
        ApprovalRequestStatus approvalRequestStatus, LocalDateTime requestedAt,
        LocalDateTime completedAt) {
        this.id = id;
        this.projectStep = projectStep;
        this.approvalRequestStatus = approvalRequestStatus;
        this.requestedAt = requestedAt;
        this.completedAt = completedAt;
    }

    public static ApprovalRequest create(ProjectStep projectStep,
        ApprovalRequestStatus approvalRequestStatus, LocalDateTime requestedAt,
        LocalDateTime completedAt) {
        return new ApprovalRequest(null, projectStep, approvalRequestStatus, requestedAt,
            completedAt);
    }

    public void approve() {
        this.approvalRequestStatus = ApprovalRequestStatus.APPROVED;
        this.completedAt = LocalDateTime.now();
    }

    public void reject() {
        this.approvalRequestStatus = ApprovalRequestStatus.REJECTED;
        this.completedAt = LocalDateTime.now();
    }
}
