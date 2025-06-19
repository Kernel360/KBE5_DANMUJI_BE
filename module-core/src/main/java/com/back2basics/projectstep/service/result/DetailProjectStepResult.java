package com.back2basics.projectstep.service.result;

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

    public static DetailProjectStepResult toResult(ProjectStep step) {
        return DetailProjectStepResult.builder()
            .id(step.getId())
            .name(step.getName())
            .stepOrder(step.getStepOrder())
            .projectStepStatus(step.getProjectStepStatus())
            .build();
    }
}
