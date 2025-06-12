package com.back2basics.domain.projectstep.dto.request;

import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import jakarta.validation.constraints.NotBlank;


public record UpdateProjectStepRequest(@NotBlank String name, Long userId) {

    public UpdateProjectStepCommand toCommand() {
        return UpdateProjectStepCommand.builder()
            .name(name)
            .userId(userId)
//            .projectStepStatus(projectStepStatus)
//            .projectFeedbackStepStatus(projectFeedbackStepStatus)
            .build();
    }
}
