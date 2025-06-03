package com.back2basics.project.service;

import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.ReadProjectUseCase;
import com.back2basics.project.port.out.ReadProjectPort;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadProjectService implements ReadProjectUseCase {

    private final ReadProjectPort port;
    private final ReadProjectStepPort stepPort;
    private final ProjectValidator projectValidator;

    // todo : filtering - status IN_PROGRESS / COMPLETED
    @Override
    public ProjectGetResult getProjectById(Long id) {
        Project project = projectValidator.findProjectById(id);
        List<ProjectStep> steps = stepPort.findAllByProjectId(project.getId());
        project.setSteps(steps);
        return ProjectGetResult.toResult(project);
    }

    @Override
    public List<ProjectGetResult> getAllProjects() {
        List<Project> projects = port.findAll();
        for (Project project : projects) {
            List<ProjectStep> steps = stepPort.findAllByProjectId(project.getId());
            project.setSteps(steps);
        }
        return projects.stream()
            .map(ProjectGetResult::toResult)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProjectGetResult> getAllProjectsByUserId(Long userId) {
        return List.of();
    }
}
