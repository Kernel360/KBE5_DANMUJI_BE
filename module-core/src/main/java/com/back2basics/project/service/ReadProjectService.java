package com.back2basics.project.service;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.ReadProjectUseCase;
import com.back2basics.project.port.out.ReadProjectPort;
import com.back2basics.project.service.result.ProjectDetailResult;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.project.service.result.ProjectRecentGetResult;
import com.back2basics.project.service.result.ProjectListResult;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import com.back2basics.assignment.port.out.AssignmentQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadProjectService implements ReadProjectUseCase {

    private final ReadProjectPort readProjectPort;
    private final ReadProjectStepPort readProjectStepPort;
    private final ProjectValidator projectValidator;
    private final AssignmentQueryPort assignmentQueryPort;

    @Override
    public Page<ProjectGetResult> getAllProjects(Pageable pageable) {
        Page<Project> projects = readProjectPort.findAll(pageable);
        // todo: mapper 에서 추가하면 따로 set 해줄 필요 없음.
        for (Project project : projects) {
            List<ProjectStep> steps = readProjectStepPort.findAllByProjectId(project.getId());
            project.setSteps(steps);
            List<Assignment> assignments = assignmentQueryPort.findUsersByProjectId(
                project.getId());
            project.setUsers(assignments);
        }
        return projects
            .map(ProjectGetResult::toResult);

    }

    @Override
    public Page<ProjectGetResult> searchProjects(String keyword, Pageable pageable) {
        Page<Project> projects = readProjectPort.searchByKeyword(keyword, pageable);
        for (Project project : projects) {
            List<ProjectStep> steps = readProjectStepPort.findAllByProjectId(project.getId());
            project.setSteps(steps);
            List<Assignment> assignments = assignmentQueryPort.findUsersByProjectId(
                project.getId());
            project.setUsers(assignments);
        }
        return projects.map(ProjectGetResult::toResult);
    }

    @Override
    public List<ProjectGetResult> getAllProjects() {
        List<Project> projects = readProjectPort.getAllProjects();
        for (Project project : projects) {
            List<ProjectStep> steps = readProjectStepPort.findAllByProjectId(project.getId());
            project.setSteps(steps);
            List<Assignment> assignments = assignmentQueryPort.findUsersByProjectId(
                project.getId());
            project.setUsers(assignments);
        }
        return projects.stream()
            .map(ProjectGetResult::toResult).toList();
    }

    @Override
    public ProjectDetailResult getProjectDetails(Long projectId) {
        Project project = projectValidator.findProjectById(projectId);
        return ProjectDetailResult.of(project);
    }

    @Override
    public List<ProjectRecentGetResult> getRecentProjects() {
        return readProjectPort.getRecentProjects().stream().map(ProjectRecentGetResult::toResult).toList();
    }

    @Override
    public Page<ProjectListResult> getUserProjects(Long userId, Pageable pageable) {
        Page<Project> projects = readProjectPort.findAllByUserId(userId, pageable);
        return projects.map(ProjectListResult::toResult);
    }

    @Override
    public Page<ProjectListResult> getAllByUserIdOne(Long userId, Pageable pageable) {
        Page<Project> projects = readProjectPort.findAllByUserIdOne(userId, pageable);
        return projects.map(ProjectListResult::toResult);
    }
}
