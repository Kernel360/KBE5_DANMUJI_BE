package com.back2basics.projectstep.service.result;

import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;

public record ProjectStepSimpleResult(Long id, int stepOrder, String name,
                                      ProjectStepStatus status) {

    public static ProjectStepSimpleResult from(ProjectStep projectStep) {
        return new ProjectStepSimpleResult(
            projectStep.getStepId(),
            projectStep.getStepOrder(),
            projectStep.getName(),
            projectStep.getProjectStepStatus()
        );
    }
}
