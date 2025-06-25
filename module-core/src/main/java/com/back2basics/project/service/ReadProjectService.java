package com.back2basics.project.service;

import com.back2basics.assignment.port.out.AssignmentQueryPort;
import com.back2basics.company.model.CompanyType;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.ReadProjectUseCase;
import com.back2basics.project.port.out.ReadProjectPort;
import com.back2basics.project.service.result.ProjectDetailResult;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.project.service.result.ProjectRecentGetResult;
import com.back2basics.project.service.result.ProjectListResult;
import com.back2basics.user.model.UserType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadProjectService implements ReadProjectUseCase {

    private final ReadProjectPort readProjectPort;
    private final ProjectValidator projectValidator;
    private final UserValidator userValidator;
    private final AssignmentQueryPort assignmentQueryPort;

    @Override
    public Page<ProjectListResult> getAllProjects(Pageable pageable) {
        Page<Project> projects = readProjectPort.findAll(pageable);
        return projects.map(ProjectListResult::toResult);
    }

    @Override
    public Page<ProjectGetResult> searchProjects(String keyword, Pageable pageable) {
        Page<Project> projects = readProjectPort.searchByKeyword(keyword, pageable);
        return projects.map(ProjectGetResult::toResult);
    }

    @Override
    public List<ProjectGetResult> getAllProjects() {
        List<Project> projects = readProjectPort.getAllProjects();
        return projects.stream()
            .map(ProjectGetResult::toResult).toList();
    }

    @Override
    public ProjectDetailResult getProjectDetails(Long projectId, Long userId) {
        Project project = projectValidator.findById(projectId);
        UserType userType = assignmentQueryPort.findUserTypeByProjectIdAndUserId(projectId, userId);
        CompanyType companyType = assignmentQueryPort.findCompanyTypeByProjectIdAndUserId(projectId, userId);
        return ProjectDetailResult.of(project, userType, companyType);
    }

    @Override
    public List<ProjectRecentGetResult> getRecentProjects() {
        return readProjectPort.getRecentProjects().stream().map(ProjectRecentGetResult::toResult)
            .toList();
    }

    @Override
    public Page<ProjectListResult> getUserProjects(Long userId, Pageable pageable) {
        userValidator.validateNotFoundUserId(userId);
        Page<Project> projects = readProjectPort.findAllByUserId(userId, pageable);
        return projects.map(ProjectListResult::toResult);
    }

    @Override
    public Page<ProjectListResult> getAllByUserIdOne(Long userId, Pageable pageable) {
        userValidator.validateNotFoundUserId(userId);
        Page<Project> projects = readProjectPort.findAllByUserIdOne(userId, pageable);
        return projects.map(ProjectListResult::toResult);
    }
}
