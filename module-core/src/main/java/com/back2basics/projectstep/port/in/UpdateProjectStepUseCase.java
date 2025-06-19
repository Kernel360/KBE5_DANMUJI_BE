package com.back2basics.projectstep.port.in;

import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;

public interface UpdateProjectStepUseCase {

    void updateStepName(UpdateProjectStepCommand command, Long stepId);

    void updateApprovalStatus(ProjectStepStatus projectStepStatus, Long stepId);
}
