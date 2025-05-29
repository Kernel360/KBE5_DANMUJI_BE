package com.back2basics.project.service;

import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.DeleteProjectUseCase;
import com.back2basics.project.port.in.GetProjectUseCase;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.project.port.out.ProjectRepositoryPort;
import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.project.service.result.ProjectUpdateResult;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements
    CreateProjectUseCase,
    GetProjectUseCase,
    UpdateProjectUseCase,
    DeleteProjectUseCase {

    private final ProjectRepositoryPort projectRepositoryPort;
    private final ProjectValidator projectValidator;

    @Override
    public void createProject(ProjectCreateCommand command) {
        Project project = Project.builder()
            .name(command.getName())
            .description(command.getDescription())
            .startDate(command.getStartDate())
            .endDate(command.getEndDate())
            .isDeleted(false)
            .build();
        projectRepositoryPort.save(project);
    }

    // todo : filtering - status IN_PROGRESS / COMPLETED
    @Override
    public ProjectGetResult getProjectById(Long id) {
        Project project = projectValidator.findProjectById(id);
        return ProjectGetResult.toResult(project);
    }

    @Override
    public List<ProjectGetResult> getAllProjects() {
        return projectRepositoryPort.findAll().stream()
            .map(ProjectGetResult::toResult)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProjectGetResult> getAllProjectsByUserId(Long userId) {
        return List.of();
    }

    // todo : 사용자 인증 로직 추가
    @Override
    public ProjectUpdateResult updateProject(Long id,
        ProjectUpdateCommand projectUpdateCommand) {
        Project project = projectValidator.findProjectById(id);
        project.update(projectUpdateCommand);
        projectRepositoryPort.update(project);
        return ProjectUpdateResult.toResult(project);
    }

    // todo : softDelete 의견 공유
    @Override
    public void deleteProject(Long id) {
        Project project = projectValidator.findProjectById(id);
        project.softDeleted();
        projectRepositoryPort.update(project);
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
