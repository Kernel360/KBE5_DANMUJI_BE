package com.back2basics.service.project;

import com.back2basics.model.project.Project;
import com.back2basics.port.in.project.CreateProjectUseCase;
import com.back2basics.port.in.project.DeleteProjectUseCase;
import com.back2basics.port.in.project.GetProjectUseCase;
import com.back2basics.port.in.project.UpdateProjectUseCase;
import com.back2basics.port.out.project.ProjectRepositoryPort;
import com.back2basics.service.project.dto.ProjectCreateCommand;
import com.back2basics.service.project.dto.ProjectResponseDto;
import com.back2basics.service.project.dto.ProjectUpdateCommand;
import com.back2basics.service.project.mapper.ProjectCommandMapper;
import com.back2basics.service.project.validation.ProjectValidator;
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
            .build();
        projectRepositoryPort.save(project);
    }

    @Override
    public ProjectResponseDto getProjectById(Long id) {
        Project project = projectValidator.findProject(id);
        return ProjectCommandMapper.from(project);
    }

    @Override
    public List<ProjectResponseDto> getAllProjects() {
        return projectRepositoryPort.findAll().stream()
            .map(ProjectCommandMapper::from)
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
        return ProjectCommandMapper.from(project);
    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectValidator.findProject(id);
        project.softDelete();
        projectRepositoryPort.update(project);
    }
}
