package com.back2basics.projectstep.service.result;

import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReadProjectStepResult {

    private final Long id;

    private final Long projectId;

    private String name;

    private Integer stepOrder;

    private ProjectStepStatus projectStepStatus;

    public static ReadProjectStepResult toResult(ProjectStep step) {
        return ReadProjectStepResult.builder()
            .id(step.getStepId())
            .projectId(step.getProjectId())
            .name(step.getName())
            .stepOrder(step.getStepOrder())
            .projectStepStatus(step.getProjectStepStatus())
            .build();
    }
}
