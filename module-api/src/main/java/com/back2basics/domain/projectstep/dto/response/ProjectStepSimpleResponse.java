package com.back2basics.domain.projectstep.dto.response;

import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.service.result.ProjectStepSimpleResult;
import com.back2basics.user.model.User;

public record ProjectStepSimpleResponse(Long id, int stepOrder, String name,
                                        ProjectStepStatus projectStepStatus, ProjectFeedbackStepStatus projectFeedbackStepStatus, boolean isDeleted, User user) {

    public static ProjectStepSimpleResponse from(ProjectStepSimpleResult projectStepSimpleResult) {
        return new ProjectStepSimpleResponse(
            projectStepSimpleResult.id(),
            projectStepSimpleResult.stepOrder(),
            projectStepSimpleResult.name(),
            projectStepSimpleResult.projectStepStatus(),
            projectStepSimpleResult.projectFeedbackStepStatus(),
            projectStepSimpleResult.isDeleted(),
            projectStepSimpleResult.user()
        );
    }
}
