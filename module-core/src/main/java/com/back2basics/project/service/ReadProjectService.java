package com.back2basics.project.service;

import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.ReadProjectUseCase;
import com.back2basics.project.port.out.ReadProjectPort;
import com.back2basics.project.service.result.ProjectDetailResult;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.project.service.result.ProjectRecentGetResult;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import com.back2basics.projectuser.model.ProjectUser;
import com.back2basics.projectuser.port.out.ProjectUserQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadProjectService implements ReadProjectUseCase {

    private final ReadProjectPort port;
    private final ReadProjectStepPort stepPort;
    private final ProjectValidator projectValidator;
    private final ProjectUserQueryPort projectUserQueryPort;
    private final ReadProjectStepPort readProjectStepPort;

    @Override
    public Page<ProjectGetResult> getAllProjects(Pageable pageable) {
        Page<Project> projects = port.findAll(pageable);
        for (Project project : projects) {
            List<ProjectStep> steps = stepPort.findAllByProjectId(project.getId());
            project.setSteps(steps);
            List<ProjectUser> projectUsers = projectUserQueryPort.findUsersByProjectId(
                project.getId());
            project.setUsers(projectUsers);
        }
        return projects
            .map(ProjectGetResult::toResult);
    }

    @Override
    public Page<ProjectGetResult> getAllProjectsByUserId(Long userId, Pageable pageable) {
        Page<Project> projects = port.findAllByUserId(userId, pageable);
        for (Project project : projects) {
            List<ProjectStep> steps = stepPort.findAllByProjectId(project.getId());
            project.setSteps(steps);
            List<ProjectUser> projectUsers = projectUserQueryPort.findUsersByProjectId(
                project.getId());
            project.setUsers(projectUsers);
        }
        return projects
            .map(ProjectGetResult::toResult);
    }

    @Override
    public Page<ProjectGetResult> searchProjects(String keyword, Pageable pageable) {
        return port.searchByKeyword(keyword, pageable)
            .map(ProjectGetResult::toResult);
    }

    @Override
    public ProjectDetailResult getProjectDetails(Long projectId) {
        Project project = projectValidator.findProjectById(projectId);
        List<ProjectStep> steps = readProjectStepPort.findByProjectId(projectId);
        List<ProjectUser> users = projectUserQueryPort.findUsersByProjectId(projectId);

        return ProjectDetailResult.of(project, steps, users);
    }

    @Override
    public List<ProjectRecentGetResult> getRecentProjects() {
        return port.getRecentProjects().stream().map(ProjectRecentGetResult::toResult).toList();
    }
}
