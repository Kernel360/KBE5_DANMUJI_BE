package com.back2basics.projectstep.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectStep {

    private final Long stepId;

    private final Long projectId;

    private String name;

    private int stepOrder;

    private ProjectStepStatus projectStepStatus;

    private boolean isDeleted;

    private LocalDateTime deletedAt;

    @Builder
    public ProjectStep(Long stepId, Long projectId, String name, int stepOrder,
        ProjectStepStatus projectStepStatus, boolean isDeleted, LocalDateTime deletedAt) {
        this.stepId = stepId;
        this.projectId = projectId;
        this.name = name;
        this.stepOrder = stepOrder;
        this.projectStepStatus = projectStepStatus;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

    public static ProjectStep create(Long projectId, String name, int stepOrder,
        ProjectStepStatus projectStepStatus) {
        return new ProjectStep(null, projectId, name, stepOrder, projectStepStatus, false, null);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateStatus(ProjectStepStatus projectStepStatus) {
        this.projectStepStatus = projectStepStatus;
    }

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void updateStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }

}
