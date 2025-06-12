package com.back2basics.projectstep.port.in.command;

import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.model.ProjectStepStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateProjectStepCommand {

    @NotBlank
    private final String name;
    private final Long userId;
//    private final ProjectStepStatus projectStepStatus;
//    private final ProjectFeedbackStepStatus projectFeedbackStepStatus;
}
