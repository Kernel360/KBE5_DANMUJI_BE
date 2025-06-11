package com.back2basics.domain.projectstep.dto.response;

import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.service.result.DetailProjectStepResult;

public record DetailProjectStepResponse(Long id, String name, Long userId, String userName,
                                        Integer stepOrder, ProjectStepStatus projectStepStatus,
                                        ProjectFeedbackStepStatus projectFeedbackStepStatus) {

    public static DetailProjectStepResponse toResponse(DetailProjectStepResult result) {
        return new DetailProjectStepResponse(result.getId(), result.getName(), result.getUserId(),
            result.getUserName(), result.getStepOrder(), result.getProjectStepStatus(),
            result.getProjectFeedbackStepStatus());
    }
}
