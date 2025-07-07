package com.back2basics.adapter.persistence.checklist.entity;


import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.model.ApprovalStatus;
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
@Table(name = "approvals")
public class ApprovalEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    private ChecklistEntity checklist;

    @Column(name = "message")
    private String message;

    @Column
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    public ApprovalEntity(ChecklistEntity checklist, UserEntity user) {
        this.checklist = checklist;
        this.user = user;
        this.status = ApprovalStatus.PENDING;
    }

    public ApprovalEntity(Approval approval, ChecklistEntity checklist, UserEntity user) {
        this.id = approval.getId();
        this.user = user;
        this.checklist = checklist;
        this.message = approval.getMessage();
        this.status = approval.getStatus();
        this.respondedAt = approval.getRespondedAt();
    }

}
