package com.back2basics.projectstep.port.in.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateProjectStepCommand {

    @NotBlank(message = "단계명은 필수입니다.")
    private String name;

    private Long userId;
}
