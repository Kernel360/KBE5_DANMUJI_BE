package com.back2basics.projectstep.service;

import static com.back2basics.projectstep.model.ProjectStepStatus.*;
import static com.back2basics.projectstep.model.ProjectFeedbackStepStatus.*;

import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.in.CreateProjectStepUseCase;
import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectStepService implements CreateProjectStepUseCase {

    private final SaveProjectStepPort port;

    @Override
    public void createStep(CreateProjectStepCommand command, Long projectId) {
        ProjectStep step = ProjectStep.builder()
            .projectId(projectId)
            .userId(command.getUserId())
            .name(command.getName())
            .stepOrder(command.getStepOrder())
            .projectStepStatus(PENDING)
            .projectFeedbackStepStatus(command.getUserId() != null ? REQUESTED : null)
            .build();

        port.save(step);
    }
}
