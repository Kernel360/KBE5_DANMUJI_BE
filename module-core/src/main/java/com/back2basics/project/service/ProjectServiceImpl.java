package com.back2basics.project.service;

import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.DeleteProjectUseCase;
import com.back2basics.project.port.in.GetProjectUseCase;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.project.port.out.ProjectRepositoryPort;
import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.project.port.in.command.ProjectResponseDto;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.infra.validation.validator.ProjectValidator;
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
            .build(); // todo: 여기서 build
        projectRepositoryPort.save(project);
    }

    @Override
    public ProjectResponseDto getProjectById(Long id) {
        Project project = projectValidator.findProject(id);
        return ProjectResponseDto.from(project);
    }

    @Override
    public List<ProjectResponseDto> getAllProjects() {
        return projectRepositoryPort.findAll().stream()
            .map(ProjectResponseDto::from)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponseDto> getAllProjectsByUserId(Long userId) {
        return List.of();
    }

    // todo : 사용자 인증 로직 추가
    @Override
    public ProjectResponseDto updateProject(Long id,
        ProjectUpdateCommand projectUpdateCommand) {
        Project project = projectValidator.findProject(id);
        project.update(projectUpdateCommand);
        projectRepositoryPort.update(project);
        return ProjectResponseDto.from(project);
    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectValidator.findProject(id);
        // project.softDeleted();
        projectRepositoryPort.update(project);
    }
}
