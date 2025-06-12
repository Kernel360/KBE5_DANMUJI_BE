package com.back2basics.projectstep.service.result;

import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.user.model.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReadProjectStepResult {

    private final Long id;

    private final Long projectId;

    private final Long userId;

    private final User user;

    private String name;

    private Integer stepOrder;

    private ProjectStepStatus projectStepStatus;

    private boolean isDeleted;

    private LocalDateTime deleteAt;

    public static ReadProjectStepResult toResult(ProjectStep step) {
        return ReadProjectStepResult.builder()
            .id(step.getStepId())
            .projectId(step.getProjectId())
            .userId(step.getUserId())
            .user(step.getUser())
            .name(step.getName())
            .stepOrder(step.getStepOrder())
            .projectStepStatus(step.getProjectStepStatus())
            .isDeleted(step.isDeleted())
            .deleteAt(step.getDeletedAt())
            .build();
    }
}
