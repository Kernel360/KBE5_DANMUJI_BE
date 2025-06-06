package com.back2basics.domain.projectstep.dto.request;

import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;
import jakarta.validation.constraints.NotBlank;

// todo: default 단계 , 추가생성은 또 따로
public record CreateProjectStepRequest(@NotBlank String name, Long userId) {

    public CreateProjectStepCommand toCommand() {
        return CreateProjectStepCommand.builder()
            .name(name)
            .userId(userId)
            .build();
    }
}
