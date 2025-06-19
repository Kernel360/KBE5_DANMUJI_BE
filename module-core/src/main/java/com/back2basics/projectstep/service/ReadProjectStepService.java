package com.back2basics.projectstep.service;

import com.back2basics.projectstep.port.in.ReadProjectStepUseCase;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import com.back2basics.projectstep.service.result.DetailProjectStepResult;
import com.back2basics.projectstep.service.result.ProjectStepSimpleResult;
import com.back2basics.projectstep.service.result.ReadProjectStepResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadProjectStepService implements ReadProjectStepUseCase {

    private final ReadProjectStepPort projectStepPort;


    @Override
    public List<ReadProjectStepResult> findAll() {

        return List.of();
    }

    @Override
    public List<ProjectStepSimpleResult> findByProjectId(Long projectId) {
        return projectStepPort.findAllByProjectId(projectId)
            .stream().map(ProjectStepSimpleResult::toResult).toList();
    }

    @Override
    public List<DetailProjectStepResult> findDetailByProjectId(Long projectId) {
        List<DetailProjectStepResult> results = projectStepPort.findAllByProjectId(projectId)
            .stream().map(DetailProjectStepResult::toResult).toList();
        return results;
    }

    @Override
    public ReadProjectStepResult findById(Long stepId) {
        return ReadProjectStepResult.toResult(projectStepPort.findById(stepId));
    }
}
