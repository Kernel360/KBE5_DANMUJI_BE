package com.back2basics.projectstep.port.in;

import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;

public interface CreateProjectStepUseCase {

    void createStep(CreateProjectStepCommand command, Long projectId, Long loggedInUserId);
}
