package com.back2basics.projectstep.model;

import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

@Getter
public class ProjectStep {

    private final Long stepId;

    private String name;

    private final Long projectId;

    private final Long userId;

    private StepStatus stepStatus;

    private ApprovalStatus approvalStatus;

    private final LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isDeleted;

    private LocalDateTime deletedAt;

    @Builder
    public ProjectStep(Long stepId, String name, Long projectId, Long userId, StepStatus stepStatus,
        ApprovalStatus approvalStatus,LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted, LocalDateTime deletedAt) {
        this.stepId = stepId;
        this.name = name;
        this.projectId = projectId;
        this.userId = userId;
        this.stepStatus = stepStatus;
        this.approvalStatus = approvalStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

    public void update(UpdateProjectStepCommand command) {
        this.name = command.getName();
        this.stepStatus = command.getStepStatus();
        this.approvalStatus = command.getApprovalStatus();
    }

    // todo: 멘토링 때 동사 현재형으로 쓰라고 헀었나 .. 기억 안남 .. 다시 물어보기
    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
