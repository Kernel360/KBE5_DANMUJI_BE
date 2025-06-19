package com.back2basics.projectstep.service;

import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.in.CreateProjectStepUseCase;
import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectStepService implements CreateProjectStepUseCase {

    private final ReadProjectStepPort readProjectStepPort;
    private final SaveProjectStepPort saveProjectStepPort;

    @Override
    public void createStep(CreateProjectStepCommand command, Long projectId) {
        Integer maxOrder = readProjectStepPort.findMaxStepOrderByProjectId(projectId);
        ProjectStep step = ProjectStep.create(projectId, command.getName(), maxOrder + 1,
            ProjectStepStatus.PENDING);

        saveProjectStepPort.save(step);
    }
}
