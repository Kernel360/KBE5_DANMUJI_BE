package com.back2basics.domain.projectstep.dto.response;

import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.service.result.DetailProjectStepResult;

public record DetailProjectStepResponse(Long id, String name, Integer stepOrder,
                                        ProjectStepStatus projectStepStatus
) {

    public static DetailProjectStepResponse toResponse(DetailProjectStepResult result) {
        return new DetailProjectStepResponse(result.getId(), result.getName(),
            result.getStepOrder(), result.getProjectStepStatus());
    }
}
