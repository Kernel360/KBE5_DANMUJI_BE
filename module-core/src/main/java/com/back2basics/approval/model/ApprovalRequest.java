package com.back2basics.approval.model;

import com.back2basics.history.strategy.TargetDomain;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ApprovalRequest implements TargetDomain {

    private final Long id;

    private final Long projectStepId;

    private final Long requesterId;

    private ApprovalRequestStatus approvalRequestStatus;

    private final LocalDateTime requestedAt;

    private LocalDateTime completedAt;

    private List<Long> responseIds;

    public ApprovalRequest(Long id, Long projectStepId, Long requesterId,
        ApprovalRequestStatus approvalRequestStatus, LocalDateTime requestedAt,
        LocalDateTime completedAt, List<Long> responseIds) {
        this.id = id;
        this.projectStepId = projectStepId;
        this.requesterId = requesterId;
        this.approvalRequestStatus = approvalRequestStatus;
        this.requestedAt = requestedAt;
        this.completedAt = completedAt;
        this.responseIds = new ArrayList<>(responseIds != null ? responseIds : List.of());
    }

    public static ApprovalRequest create(Long projectStepId, Long requesterId,
        List<Long> responseIds) {
        return new ApprovalRequest(null, projectStepId, requesterId, ApprovalRequestStatus.PENDING,
            LocalDateTime.now(), null, responseIds);
    }

    public void approve() {
        this.approvalRequestStatus = ApprovalRequestStatus.APPROVED;
        this.completedAt = LocalDateTime.now();
    }

    public void reject() {
        this.approvalRequestStatus = ApprovalRequestStatus.REJECTED;
        this.completedAt = LocalDateTime.now();
    }

    public void addResponse(Long approverId) {
        if (!this.responseIds.contains(approverId)) {
            this.responseIds.add(approverId);
        }
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public static ApprovalRequest copyOf(ApprovalRequest original) {
        return new ApprovalRequest(
            original.id,
            original.projectStepId,
            original.requesterId,
            original.approvalRequestStatus,
            original.requestedAt,
            original.completedAt,
            new ArrayList<>(original.responseIds)
        );
    }
}
