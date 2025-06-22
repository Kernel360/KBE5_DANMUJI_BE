package com.back2basics.adapter.persistence.approval.entity;

import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.approval.model.ApprovalRequestStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "approval_requests")
public class ApprovalRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id")
    private ProjectStepEntity projectStep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity requester; // 요청 생성자

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApprovalRequestStatus approvalRequestStatus;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "approvalRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApprovalResponseEntity> responses = new ArrayList<>();

    public ApprovalRequestEntity(Long id, ProjectStepEntity projectStep, UserEntity requester,
        ApprovalRequestStatus approvalRequestStatus, LocalDateTime requestedAt,
        LocalDateTime completedAt, List<ApprovalResponseEntity> responses) {
        this.id = id;
        this.projectStep = projectStep;
        this.requester = requester;
        this.approvalRequestStatus = approvalRequestStatus;
        this.requestedAt = requestedAt;
        this.completedAt = completedAt;
        this.responses = responses;
    }

    public ApprovalRequestEntity(ProjectStepEntity projectStep, UserEntity requester,
        ApprovalRequestStatus status) {
        this.projectStep = projectStep;
        this.requester = requester;
        this.approvalRequestStatus = status;
        this.requestedAt = LocalDateTime.now();
    }

    public void addResponses(List<ApprovalResponseEntity> newApprovers) {
        List<Long> existingApproverIds = this.responses.stream()
            .map(response -> response.getApprover().getId())
            .toList();

        newApprovers.stream()
            .filter(approver -> !existingApproverIds.contains(approver.getApprover().getId()))
            .forEach(this.responses::add);
    }
}
