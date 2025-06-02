package com.back2basics.domain.projectstep.dto.request;

import com.back2basics.projectstep.model.ApprovalStatus;
import com.back2basics.projectstep.model.StepStatus;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


public record UpdateProjectStepRequest(@NotBlank String name, StepStatus stepStatus, ApprovalStatus approvalStatus) {
    public UpdateProjectStepCommand toCommand() {
        return UpdateProjectStepCommand.builder()
            .name(name)
            .stepStatus(stepStatus)
            .approvalStatus(approvalStatus)
            .build();
    }
}
