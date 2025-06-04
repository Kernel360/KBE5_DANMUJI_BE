package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.projectstep.model.ProjectStep;
import org.springframework.stereotype.Component;

@Component
public class ProjectStepMapper {

    public ProjectStep toDomain(ProjectStepEntity entity) {
        return ProjectStep.builder()
            .stepId(entity.getStepId())
            .projectId(entity.getProject().getId())
            .userId(1L) // todo: 나중에 수정할 예정 entity.getUser.getId로
            .name(entity.getName())
            .stepStatus(entity.getStepStatus())
            .approvalStatus(entity.getApprovalStatus())
            .isDeleted(entity.isDeleted())
            .deletedAt(entity.getDeletedAt())
            .build();
    }

    public ProjectStepEntity toEntity(ProjectStep projectStep) {
        return ProjectStepEntity.builder()
            .name(projectStep.getName())
            .stepStatus(projectStep.getStepStatus())
            .approvalStatus(projectStep.getApprovalStatus())
            .isDeleted(projectStep.isDeleted())
            .deletedAt(projectStep.getDeletedAt())
            .build();
    }
}
