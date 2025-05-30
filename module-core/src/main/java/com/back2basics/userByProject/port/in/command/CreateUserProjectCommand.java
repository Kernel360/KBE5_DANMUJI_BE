package com.back2basics.userByProject.port.in.command;

import com.back2basics.userByProject.model.ProjectRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserProjectCommand {

    private Long projectId;

    private Long userId;

    private CreateUserProjectCommand toCommand() {
        return CreateUserProjectCommand.builder()
            .projectId(projectId)
            .userId(userId)
            .build();
    }
}
