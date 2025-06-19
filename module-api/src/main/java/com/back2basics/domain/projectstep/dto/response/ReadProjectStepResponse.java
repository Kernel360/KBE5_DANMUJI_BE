package com.back2basics.domain.projectstep.dto.response;

import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.service.result.ReadProjectStepResult;

public record ReadProjectStepResponse(Long id, Long projectId, String name, int stepOrder,
                                      ProjectStepStatus projectStepStatus) {

    public static ReadProjectStepResponse toResponse(ReadProjectStepResult result) {

        return new ReadProjectStepResponse(result.getId(), result.getProjectId(), result.getName(),
            result.getStepOrder(), result.getProjectStepStatus());
    }
}
