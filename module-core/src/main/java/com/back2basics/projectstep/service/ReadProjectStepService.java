package com.back2basics.projectstep.service;

import com.back2basics.projectstep.port.in.ReadProjectStepUseCase;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import com.back2basics.projectstep.service.result.ProjectStepResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadProjectStepService implements ReadProjectStepUseCase {

    private final ReadProjectStepPort projectStepPort;

    @Override
    public List<ProjectStepResult> findByProjectId(Long projectId) {
        return projectStepPort.findAllByProjectId(projectId)
            .stream().map(ProjectStepResult::toResult).toList();
    }

    @Override
    public ProjectStepResult findById(Long stepId) {
        return ProjectStepResult.toResult(projectStepPort.findById(stepId));
    }
}
