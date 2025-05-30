package com.back2basics.project.service;

import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.ReadProjectUseCase;
import com.back2basics.project.port.out.ReadProjectPort;
import com.back2basics.project.service.result.ProjectGetResult;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadProjectService implements ReadProjectUseCase {

    private final ReadProjectPort port;
    private final ProjectValidator projectValidator;

    // todo : filtering - status IN_PROGRESS / COMPLETED
    @Override
    public ProjectGetResult getProjectById(Long id) {
        Project project = projectValidator.findProjectById(id);
        return ProjectGetResult.toResult(project);
    }

    @Override
    public List<ProjectGetResult> getAllProjects() {
        return port.findAll().stream()
            .map(ProjectGetResult::toResult)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProjectGetResult> getAllProjectsByUserId(Long userId) {
        return List.of();
    }
}
