package com.back2basics.projectstep.service;

import com.back2basics.projectstep.model.ApprovalStatus;
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


    //todo: ApprovalStatus 승인자 있으면 REQUESTED, 없으면 null 나중에
    @Override
    public void createStep(CreateProjectStepCommand command, Long projectId) {
        ProjectStep step = ProjectStep.builder()
            .name(command.getName())
            .projectId(projectId)
            .stepStatus(command.getStepStatus())
            .approvalStatus(command.getApprovalStatus())
            .build();
        port.save(step);
    }
}
