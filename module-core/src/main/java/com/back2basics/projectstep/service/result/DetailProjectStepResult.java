package com.back2basics.projectstep.service.result;

import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DetailProjectStepResult {

    private final Long id;

    private final String name;

    private final Long userId;

    private final String userName;

    private final Integer stepOrder;

    private final ProjectStepStatus projectStepStatus;

    private final ProjectFeedbackStepStatus projectFeedbackStepStatus;

    public static DetailProjectStepResult toResult(ProjectStep step) {
        String userName = step.getUser() != null ? step.getUser().getName() : "미지정";
        return DetailProjectStepResult.builder()
            .id(step.getStepId())
            .name(step.getName())
            .userId(step.getUserId())
            .userName(userName)
            .stepOrder(step.getStepOrder())
            .projectStepStatus(step.getProjectStepStatus())
            .projectFeedbackStepStatus(step.getProjectFeedbackStepStatus())
            .build();
    }
}
