package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.projectstep.model.ProjectStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectStepMapper {

    public ProjectStep toDomain(ProjectStepEntity entity) {
        return new ProjectStep(entity.getId(), entity.getProject().getId(), entity.getName(),
            entity.getStepOrder(), entity.getProjectStepStatus());
    }

    public ProjectStepEntity toEntity(ProjectStep projectStep, ProjectEntity project) {
        ProjectStepEntity projectStepEntity = new ProjectStepEntity(projectStep.getId(), project,
            projectStep.getName(), projectStep.getStepOrder(), projectStep.getProjectStepStatus());
        if (projectStep.isDeleted()) {
            projectStepEntity.markDeleted();
        }
        return projectStepEntity;
    }
}
