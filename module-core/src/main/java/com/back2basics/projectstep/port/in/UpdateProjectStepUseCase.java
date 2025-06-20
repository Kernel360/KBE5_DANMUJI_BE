package com.back2basics.projectstep.port.in;

import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import java.util.List;

public interface UpdateProjectStepUseCase {

    void updateStepName(UpdateProjectStepCommand command, Long stepId);

    void updateApprovalStatus(ProjectStepStatus projectStepStatus, Long stepId);

    void reorderSteps(Long projectId, List<Long> stepIdsInNewOrder);
}
