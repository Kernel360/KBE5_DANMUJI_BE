package com.back2basics.domain.projectstep.dto.response;

import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.service.result.ProjectStepSimpleResult;

public record ProjectStepSimpleResponse(Long id, int stepOrder, String name,
                                        ProjectStepStatus status) {

    public static ProjectStepSimpleResponse from(ProjectStepSimpleResult projectStepSimpleResult) {
        return new ProjectStepSimpleResponse(
            projectStepSimpleResult.id(),
            projectStepSimpleResult.stepOrder(),
            projectStepSimpleResult.name(),
            projectStepSimpleResult.status()
        );
    }
}
