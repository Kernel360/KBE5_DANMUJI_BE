package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.projectstep.model.ProjectStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectStepMapper {

    public ProjectStep toDomain(ProjectStepEntity entity) {
        return new ProjectStep(entity.getStepId(), entity.getProjectId(), entity.getName(),
            entity.getStepOrder(), entity.getProjectStepStatus());
    }

    public ProjectStepEntity toEntity(ProjectStep projectStep) {
        return new ProjectStepEntity(projectStep.getStepId(), projectStep.getProjectId(),
            projectStep.getName(), projectStep.getStepOrder(), projectStep.getProjectStepStatus());
    }
}
