package com.back2basics.domain.projectstep.dto.request;

import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import jakarta.validation.constraints.NotBlank;


public record UpdateProjectStepRequest(@NotBlank String name, ProjectStepStatus projectStepStatus,
                                       ProjectFeedbackStepStatus projectFeedbackStepStatus) {

    public UpdateProjectStepCommand toCommand() {
        return UpdateProjectStepCommand.builder()
            .name(name)
            .projectStepStatus(projectStepStatus)
            .projectFeedbackStepStatus(projectFeedbackStepStatus)
            .build();
    }
}
