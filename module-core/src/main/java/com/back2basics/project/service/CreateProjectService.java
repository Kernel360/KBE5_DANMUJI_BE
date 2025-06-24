package com.back2basics.project.service;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.assignment.service.notification.AssignmentNotificationSender;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.project.port.out.SaveProjectPort;
import com.back2basics.project.port.out.SaveProjectUserPort;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectService implements CreateProjectUseCase {

    private final SaveProjectPort saveProjectPort;
    private final SaveProjectStepPort saveProjectStepPort;
    private final SaveProjectUserPort saveProjectUserPort;
    private final UserQueryPort userQueryPort;
    private final AssignmentNotificationSender assignmentNotificationSender;
    private final ProjectValidator projectValidator;

    private static final List<String> DEFAULT_STEPS =
        List.of("요구사항 정의", "화면설계", "디자인", "퍼블리싱", "개발", "검수");

    @Override
    @Transactional
    public void createProject(ProjectCreateCommand command) {
        Project project = Project.builder()
            .name(command.getName())
            .description(command.getDescription())
            .startDate(command.getStartDate())
            .endDate(command.getEndDate())
            .status(ProjectStatus.IN_PROGRESS)
            .build();
        Project savedProject = saveProjectPort.createSave(project);
        projectValidator.findById(savedProject.getId());
        createDefaultSteps(savedProject.getId());
        assignUsers(savedProject, command);
    }

    private void createDefaultSteps(Long projectId) {
        List<String> defaultSteps = DEFAULT_STEPS;
        for (int i = 0; i < defaultSteps.size(); i++) {
            ProjectStepStatus projectStepStatus =
                (i == 0) ? ProjectStepStatus.IN_PROGRESS : ProjectStepStatus.PENDING;
            ProjectStep step = ProjectStep.create(
                projectId,
                defaultSteps.get(i),
                i + 1,
                projectStepStatus
            );
            saveProjectStepPort.save(step);
        }
    }

    private void assignUsers(Project project, ProjectCreateCommand command) {
        List<User> devManagers = command.getDevManagerId().stream().map(userQueryPort::findById)
            .toList();
        List<User> clientManagers = command.getClientManagerId().stream()
            .map(userQueryPort::findById).toList();
        List<User> devUsers = command.getDevUserId().stream().map(userQueryPort::findById).toList();
        List<User> clientUsers = command.getClientUserId().stream().map(userQueryPort::findById)
            .toList();

        List<Assignment> assignments = Assignment.createProjectUser(project, devManagers,
            clientManagers, devUsers, clientUsers);
        saveProjectUserPort.saveAll(assignments);

        // 알림을 위한 assignments id 리스트
        List<Long> assignmentIds = assignments.stream()
            .map(assignment -> assignment.getUser().getId()).toList();
        assignmentNotificationSender.sendNotification(assignmentIds, project.getId());
    }
}
