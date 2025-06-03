package com.back2basics.projectstep.service.result;

import com.back2basics.projectstep.model.ApprovalStatus;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.StepStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReadProjectStepResult {

    private final Long stepId;

    private final Long projectId;

    private final Long userId;

    private String name;

    private StepStatus stepStatus;

    private ApprovalStatus approvalStatus;

    private boolean isDeleted;

    private LocalDateTime deleteAt;

    public static ReadProjectStepResult toResult(ProjectStep step) {
        return ReadProjectStepResult.builder()
            .stepId(step.getStepId())
            .projectId(step.getProjectId())
            .userId(step.getUserId())
            .name(step.getName())
            .stepStatus(step.getStepStatus())
            .approvalStatus(step.getApprovalStatus())
            .isDeleted(step.isDeleted())
            .deleteAt(step.getDeletedAt())
            .build();
    }
}
