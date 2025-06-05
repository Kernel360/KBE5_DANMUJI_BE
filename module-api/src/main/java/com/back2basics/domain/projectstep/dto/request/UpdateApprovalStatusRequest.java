package com.back2basics.domain.projectstep.dto.request;

import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateApprovalStatusRequest(
    @NotNull ProjectFeedbackStepStatus projectFeedbackStepStatus) {

}
