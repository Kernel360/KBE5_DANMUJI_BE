package com.back2basics.domain.projectstep.dto.request;

import com.back2basics.projectstep.model.ApprovalStatus;
import com.back2basics.projectstep.model.StepStatus;
import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// todo: default 단계 , 추가생성은 또 따로
public record CreateProjectStepRequest(@NotBlank String name, @NotNull StepStatus stepStatus,
                                       ApprovalStatus approvalStatus) {

    public CreateProjectStepCommand toCommand() {
        return CreateProjectStepCommand.builder()
            .name(name)
            .stepStatus(stepStatus)
            .approvalStatus(approvalStatus)
            .build();
    }
}
