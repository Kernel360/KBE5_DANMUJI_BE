package com.back2basics.projectstep.service.result;

import com.back2basics.projectstep.model.ApprovalStatus;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.StepStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReadProjectStepResult {

    private final Long stepId;

    private String name;

    private StepStatus stepStatus;

    private ApprovalStatus approvalStatus;

    public static ReadProjectStepResult toResult(ProjectStep step) {
        return ReadProjectStepResult.builder()
            .stepId(step.getStepId())
            .name(step.getName())
            .stepStatus(step.getStepStatus())
            .approvalStatus(step.getApprovalStatus())
            .build();
    }
}
