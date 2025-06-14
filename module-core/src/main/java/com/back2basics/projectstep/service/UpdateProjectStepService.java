package com.back2basics.projectstep.service;

import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepUseCase;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectStepService implements UpdateProjectStepUseCase {

    private final ReadProjectStepPort readPort;
    private final SaveProjectStepPort savePort;

    @Override
    public void updateStep(UpdateProjectStepCommand command, Long stepId) {
        ProjectStep step = readPort.findById(stepId);
        Long userId = readPort.findUserById(stepId);
        step.update(command, userId);
        savePort.save(step);
    }

    @Override
    public void updateApprovalStatus(ProjectFeedbackStepStatus projectFeedbackStepStatus,
        Long stepId) {
        ProjectStep step = readPort.findById(stepId);
        step.approvalProjectFeedbackStepStatus(projectFeedbackStepStatus);
        savePort.save(step);
    }
}
