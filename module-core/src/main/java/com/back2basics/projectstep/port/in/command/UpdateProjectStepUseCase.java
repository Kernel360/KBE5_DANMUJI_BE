package com.back2basics.projectstep.port.in.command;

public interface UpdateProjectStepUseCase {

    void updateStep(UpdateProjectStepCommand command, Long stepId);
}
