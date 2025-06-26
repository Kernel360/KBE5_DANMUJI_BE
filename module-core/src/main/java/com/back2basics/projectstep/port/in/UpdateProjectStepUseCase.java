package com.back2basics.projectstep.port.in;

import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import java.util.List;

public interface UpdateProjectStepUseCase {

    void updateStepName(UpdateProjectStepCommand command, Long stepId, Long loggedInUserId);

    void reorderSteps(Long projectId, List<Long> stepIdsInNewOrder);

    void updateStepStatus(Long stepId);

    void revertStepStatus(Long stepId);

}
