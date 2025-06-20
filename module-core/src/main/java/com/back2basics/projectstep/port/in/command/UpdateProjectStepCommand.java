package com.back2basics.projectstep.port.in.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateProjectStepCommand {

    @NotBlank
    private final String name;
}
