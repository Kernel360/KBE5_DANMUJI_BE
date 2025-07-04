package com.back2basics.projectstep.model;

import com.back2basics.history.strategy.TargetDomain;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectStep implements TargetDomain {

    private final Long id;

    private final Long projectId;

    private String name;

    private int stepOrder;

    private ProjectStepStatus projectStepStatus;

    private boolean isDeleted;

    private LocalDateTime deletedAt;

    @Builder
    public ProjectStep(Long id, Long projectId, String name, int stepOrder,
        ProjectStepStatus projectStepStatus, boolean isDeleted, LocalDateTime deletedAt) {
        this.id = id;
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

    public void updateStepStatus(ProjectStep projectStep) {
        if (projectStep.getProjectStepStatus() == ProjectStepStatus.IN_PROGRESS) {
            this.projectStepStatus = ProjectStepStatus.COMPLETED;
        } else if (projectStep.getProjectStepStatus() == ProjectStepStatus.COMPLETED) {
            this.projectStepStatus = ProjectStepStatus.IN_PROGRESS;
        } else if (projectStep.getProjectStepStatus() == ProjectStepStatus.PENDING) {
            this.projectStepStatus = ProjectStepStatus.IN_PROGRESS;
        }
    }

    public void revertStepStatus() {
        this.projectStepStatus = ProjectStepStatus.PENDING;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public static ProjectStep copyOf(ProjectStep original) {
        return ProjectStep.builder()
            .id(original.id)
            .projectId(original.projectId)
            .name(original.name)
            .stepOrder(original.stepOrder)
            .projectStepStatus(original.projectStepStatus)
            .isDeleted(original.isDeleted)
            .deletedAt(original.deletedAt)
            .build();

    }
}
