package com.back2basics.projectstep.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectStep {

    private Long stepId;

    private String name;

    private Long projectId;

    private Long userId;

    private StepStatus stepStatus;

    private ApprovalStatus approvalStatus;

    @Builder
    public ProjectStep(Long stepId, String name, Long projectId, Long userId, StepStatus stepStatus,
        ApprovalStatus approvalStatus) {
        this.stepId = stepId;
        this.name = name;
        this.projectId = projectId;
        this.userId = userId;
        this.stepStatus = stepStatus;
        this.approvalStatus = approvalStatus;
    }
}
