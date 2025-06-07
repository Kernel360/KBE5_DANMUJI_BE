package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.projectstep.model.ProjectStep;
import org.springframework.stereotype.Component;

@Component
public class ProjectStepMapper {

    public ProjectStep toDomain(ProjectStepEntity entity) {
        return ProjectStep.builder()
            .stepId(entity.getStepId())
            .projectId(entity.getProject().getId())
            .userId(entity.getUser() != null ? entity.getUser().getId() : null)
            .name(entity.getName())
            .stepOrder(entity.getStepOrder())
            .projectStepStatus(entity.getProjectStepStatus())
            .isDeleted(entity.isDeleted())
            .deletedAt(entity.getDeletedAt())
            .build();
    }

    public ProjectStepEntity toEntity(ProjectStep projectStep) {
        return ProjectStepEntity.builder()
            .stepId(projectStep.getStepId())
            .name(projectStep.getName())
            .stepOrder(projectStep.getStepOrder())
            .projectStepStatus(projectStep.getProjectStepStatus())
            .isDeleted(projectStep.isDeleted())
            .deletedAt(projectStep.getDeletedAt())
            .build();
    }
}
