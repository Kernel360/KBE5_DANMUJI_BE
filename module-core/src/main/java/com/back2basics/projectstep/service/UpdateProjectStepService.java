package com.back2basics.projectstep.service;

import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.in.UpdateProjectStepUseCase;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectStepService implements UpdateProjectStepUseCase {

    private final ReadProjectStepPort readPort;
    private final SaveProjectStepPort savePort;

    @Override
    public void updateStepName(UpdateProjectStepCommand command, Long stepId) {
        ProjectStep step = readPort.findById(stepId);
        step.updateName(command.getName());
        savePort.save(step);
    }

    @Override
    public void updateApprovalStatus(ProjectStepStatus projectStepStatus,
        Long stepId) {
        ProjectStep step = readPort.findById(stepId);
        step.updateStatus(projectStepStatus);
        savePort.save(step);
    }
}
