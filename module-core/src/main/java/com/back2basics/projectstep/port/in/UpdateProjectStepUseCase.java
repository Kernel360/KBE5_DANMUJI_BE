package com.back2basics.projectstep.port.in;

import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;

public interface UpdateProjectStepUseCase {

    void updateStep(UpdateProjectStepCommand command, Long stepId);

    void updateApprovalStatus(ProjectFeedbackStepStatus projectFeedbackStepStatus, Long stepId);
}
