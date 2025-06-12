package com.back2basics.projectstep.service.result;

import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.user.model.User;

public record ProjectStepSimpleResult(Long id, int stepOrder, String name,
                                      ProjectStepStatus projectStepStatus, ProjectFeedbackStepStatus projectFeedbackStepStatus, boolean isDeleted, User user) {

    public static ProjectStepSimpleResult from(ProjectStep projectStep) {
        return new ProjectStepSimpleResult(
            projectStep.getStepId(),
            projectStep.getStepOrder(),
            projectStep.getName(),
            projectStep.getProjectStepStatus(),
            projectStep.getProjectFeedbackStepStatus(),
            projectStep.isDeleted(),
            projectStep.getUser()
        );
    }
}
