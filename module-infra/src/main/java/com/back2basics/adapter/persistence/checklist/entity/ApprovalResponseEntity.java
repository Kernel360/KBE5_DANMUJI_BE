package com.back2basics.adapter.persistence.checklist.entity;


import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.checklist.model.ApprovalResponse;
import com.back2basics.checklist.model.ApprovalResponseStatus;
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
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "approval_responses")
public class ApprovalResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity approver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_request_id")
    private ChecklistEntity approvalRequest;

    @Column(name = "message")
    private String message;

    @Column
    @Enumerated(EnumType.STRING)
    private ApprovalResponseStatus status;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    public ApprovalResponseEntity(ChecklistEntity approvalRequest, UserEntity approver) {
        this.approvalRequest = approvalRequest;
        this.approver = approver;
        this.status = ApprovalResponseStatus.PENDING;
    }

    public ApprovalResponseEntity(ApprovalResponse response,
        ChecklistEntity approvalRequest, UserEntity approver) {
        this.id = response.getId();
        this.approver = approver;
        this.approvalRequest = approvalRequest;
        this.message = response.getMessage();
        this.status = response.getStatus();
        this.respondedAt = response.getRespondedAt();
    }

    public void updateFromDomain(ApprovalResponse domain) {
        this.status = domain.getStatus();
        this.message = domain.getMessage();
        this.respondedAt = domain.getRespondedAt();
    }
}
