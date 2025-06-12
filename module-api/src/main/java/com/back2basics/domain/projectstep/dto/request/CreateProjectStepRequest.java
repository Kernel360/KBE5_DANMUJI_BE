package com.back2basics.domain.projectstep.dto.request;

import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;
import jakarta.validation.constraints.NotBlank;

public record CreateProjectStepRequest(@NotBlank String name, Long userId, int stepOrder) {

    public CreateProjectStepCommand toCommand() {
        return CreateProjectStepCommand.builder()
            .name(name)
            .userId(userId)
            .stepOrder(stepOrder)
            .build();
    }
}
