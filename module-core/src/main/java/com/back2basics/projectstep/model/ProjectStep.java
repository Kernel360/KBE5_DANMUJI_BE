package com.back2basics.projectstep.model;

import lombok.Getter;

@Getter
public class ProjectStep {

    private final Long id;

    private final Long projectId;

    private String name;

    private int stepOrder;

    private ProjectStepStatus projectStepStatus;

    private boolean isDeleted;

    public ProjectStep(Long id, Long projectId, String name, int stepOrder,
        ProjectStepStatus projectStepStatus) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.stepOrder = stepOrder;
        this.projectStepStatus = projectStepStatus;
    }

    public static ProjectStep create(Long projectId, String name, int stepOrder,
        ProjectStepStatus projectStepStatus) {
        return new ProjectStep(null, projectId, name, stepOrder, projectStepStatus);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateStatus(ProjectStepStatus projectStepStatus) {
        this.projectStepStatus = projectStepStatus;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    public void updateStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }

}
