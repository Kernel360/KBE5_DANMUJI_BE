package com.back2basics.domain.projectstep.dto.response;

import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.service.result.ProjectStepResult;

public record ProjectStepResponse(Long id, int stepOrder, String name,
                                  ProjectStepStatus projectStepStatus) {

    public static ProjectStepResponse toResponse(
        ProjectStepResult projectStepResult) {
        return new ProjectStepResponse(
            projectStepResult.id(),
            projectStepResult.stepOrder(),
            projectStepResult.name(),
            projectStepResult.projectStepStatus()
        );
    }
}
