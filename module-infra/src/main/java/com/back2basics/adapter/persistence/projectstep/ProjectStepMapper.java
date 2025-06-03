package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.projectstep.model.ProjectStep;
import org.springframework.stereotype.Component;

@Component
public class ProjectStepMapper {
    public ProjectStep toDomain(ProjectStepEntity entity) {
        return ProjectStep.builder()
            .stepId(entity.getStepId())
            .projectId(entity.getProject().getId())
            //.userId(entity.getUser().getId())
            .name(entity.getName())
            .stepStatus(entity.getStepStatus())
            .approvalStatus(entity.getApprovalStatus())
            .build();
    }

    public ProjectStepEntity toEntity(ProjectStep projectStep) {
        return ProjectStepEntity.builder()
            .stepId(projectStep.getStepId())
            .name(projectStep.getName())
            .stepStatus(projectStep.getStepStatus())
            .approvalStatus(projectStep.getApprovalStatus())
            .build();
    }
}
