package com.back2basics.projectstep.port.in.command;

import com.back2basics.projectstep.model.ApprovalStatus;
import com.back2basics.projectstep.model.StepStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateProjectStepCommand {

    @NotBlank
    private final String name;
    private final StepStatus stepStatus;
    private final ApprovalStatus approvalStatus;
}
