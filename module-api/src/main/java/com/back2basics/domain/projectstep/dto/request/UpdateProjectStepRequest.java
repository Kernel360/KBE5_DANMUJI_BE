package com.back2basics.domain.projectstep.dto.request;

import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import jakarta.validation.constraints.NotBlank;


public record UpdateProjectStepRequest(@NotBlank String name
) {

    public UpdateProjectStepCommand toCommand() {
        return UpdateProjectStepCommand.builder()
            .name(name)
            .build();
    }
}
