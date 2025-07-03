package com.back2basics.adapter.persistence.checklist.entity;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.checklist.model.ChecklistStatus;
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
@Table(name = "checklists")
public class ChecklistEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id")
    private ProjectStepEntity projectStep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ChecklistStatus checklistStatus;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "approvalRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApprovalResponseEntity> responses = new ArrayList<>();

    public ChecklistEntity(Long id, ProjectStepEntity projectStep, UserEntity user,
        String title, String content, ChecklistStatus checklistStatus, LocalDateTime requestedAt,
        LocalDateTime completedAt, List<ApprovalResponseEntity> responses) {
        this.id = id;
        this.projectStep = projectStep;
        this.user = user;
        this.title = title;
        this.content = content;
        this.checklistStatus = checklistStatus;
        this.requestedAt = requestedAt;
        this.completedAt = completedAt;
        this.responses = responses;
    }

    public ChecklistEntity(ProjectStepEntity projectStep, UserEntity user, String title,
        String content, ChecklistStatus status) {
        this.projectStep = projectStep;
        this.user = user;
        this.title = title;
        this.content = content;
        this.checklistStatus = status;
        this.requestedAt = LocalDateTime.now();
    }

    public void addResponses(List<ApprovalResponseEntity> newApprovers) {
        if (this.responses == null) {
            this.responses = new ArrayList<>();
        }

        List<Long> existingApproverIds = this.responses.stream()
            .map(response -> response.getApprover().getId())
            .toList();

        newApprovers.stream()
            .filter(approver -> !existingApproverIds.contains(approver.getApprover().getId()))
            .forEach(this.responses::add);
    }
}
