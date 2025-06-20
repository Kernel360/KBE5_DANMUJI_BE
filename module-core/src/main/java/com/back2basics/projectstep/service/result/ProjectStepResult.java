package com.back2basics.projectstep.service.result;

import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;

public record ProjectStepResult(Long id, int stepOrder, String name,
                                ProjectStepStatus projectStepStatus) {

    public static ProjectStepResult toResult(ProjectStep projectStep) {
        return new ProjectStepResult(
            projectStep.getId(),
            projectStep.getStepOrder(),
            projectStep.getName(),
            projectStep.getProjectStepStatus()
        );
    }
}
