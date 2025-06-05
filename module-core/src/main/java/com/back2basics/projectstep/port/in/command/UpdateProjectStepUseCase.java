package com.back2basics.projectstep.port.in.command;

import com.back2basics.projectstep.model.ApprovalStatus;

public interface UpdateProjectStepUseCase {

    void updateStep(UpdateProjectStepCommand command, Long stepId);

    void updateApprovalStatus(ApprovalStatus approvalStatus, Long stepId);
}
