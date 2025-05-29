package com.back2basics.project.service;

import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.project.port.out.ProjectRepositoryPort;
import com.back2basics.project.service.result.ProjectUpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectService implements UpdateProjectUseCase {

    private final ProjectRepositoryPort projectRepositoryPort;
    private final ProjectValidator projectValidator;

    // todo : 사용자 인증 로직 추가
    @Override
    public ProjectUpdateResult updateProject(Long id,
        ProjectUpdateCommand projectUpdateCommand) {
        Project project = projectValidator.findProjectById(id);
        project.update(projectUpdateCommand);
        projectRepositoryPort.update(project);
        return ProjectUpdateResult.toResult(project);
    }

    @Override
    public ProjectUpdateResult changedStatus(Long projectId) {
        Project project = projectValidator.findProjectById(projectId);

        if (project.getStatus() == ProjectStatus.IN_PROGRESS) {
            project.statusCompleted();
        } else {
            project.statusInProgress();
        }
        projectRepositoryPort.update(project);
        return ProjectUpdateResult.toResult(project);
    }
}
