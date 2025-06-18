package com.back2basics.project.service;

import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.project.port.out.SaveProjectPort;
import com.back2basics.project.port.out.SaveProjectUserPort;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import com.back2basics.assignment.model.Assignment;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserCommandPort;
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
    private final UserCommandPort userCommandPort;

    private static final List<String> DEFAULT_STEPS =
        List.of("요구사항 정의", "화면설계"); // , "디자인", "퍼블리싱", "개발", "검수"

    /* todo: refactor
        project builder 도메인으로 이동 및 페턴 변경 고려
        assignments 생성 로직 및 request 변경 <- 업체, 담당자 여러개 할당 가능 */
    @Override
    @Transactional
    public void createProject(ProjectCreateCommand command) {
        Project project = Project.builder()
            .name(command.getName())
            .description(command.getDescription())
            .startDate(command.getStartDate())
            .endDate(command.getEndDate())
            .build();
        Project savedProject = saveProjectPort.save(project);
        createDefaultSteps(savedProject.getId());
        assignMembers(savedProject, command);
    }

    private void createDefaultSteps(Long projectId) {
        List<String> defaultSteps = DEFAULT_STEPS;
        for (int i = 0; i < defaultSteps.size(); i++) {
            ProjectStep step = ProjectStep.create(
                defaultSteps.get(i),
                projectId,
                null,
                i + 1,
                ProjectStepStatus.PENDING
            );
            saveProjectStepPort.save(step);
        }
    }

    private void assignMembers(Project project, ProjectCreateCommand command) {
        List<User> devManagers = command.getDevManagerId().stream().map(userQueryPort::findById).toList();
        List<User> clientManagers = command.getClientManagerId().stream().map(userQueryPort::findById).toList();
        List<User> devUsers = command.getDevUserId().stream().map(userQueryPort::findById).toList();
        List<User> clientUsers = command.getClientUserId().stream().map(userQueryPort::findById).toList();

        List<Assignment> assignments = Assignment.createProjectUser(project, devManagers,
            clientManagers, devUsers, clientUsers);
        saveProjectUserPort.saveAll(assignments);
    }
}
