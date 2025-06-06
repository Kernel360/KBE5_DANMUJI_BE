package com.back2basics.projectstep.port.in.command;

import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;

public interface UpdateProjectStepUseCase {

    void updateStep(UpdateProjectStepCommand command, Long stepId);

    void updateApprovalStatus(ProjectFeedbackStepStatus projectFeedbackStepStatus, Long stepId);
}
