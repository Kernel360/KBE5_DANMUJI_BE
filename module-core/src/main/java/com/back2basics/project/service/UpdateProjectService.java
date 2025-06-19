package com.back2basics.project.service;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.assignment.port.out.AssignmentQueryPort;
import com.back2basics.company.model.CompanyType;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.project.port.out.ReadProjectPort;
import com.back2basics.project.port.out.SaveProjectUserPort;
import com.back2basics.project.port.out.UpdateProjectPort;
import com.back2basics.user.model.UserType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectService implements UpdateProjectUseCase {

    private final UpdateProjectPort port;
    private final ProjectValidator projectValidator;
    private final AssignmentQueryPort assignmentQueryPort;
    private final SaveProjectUserPort saveProjectUserPort;

    private final ReadProjectPort readProjectPort;


    // todo : 사용자 인증 로직 추가
    @Override
    @Transactional
    public void updateProject(Long id,
        ProjectUpdateCommand command) {

        Project project = projectValidator.findProjectById(id);
        project.update(command);
        port.update(project);

        Assignment oldDeveloper = assignmentQueryPort.findByProjectIdAndUserTypeAndCompanyType(id,
            UserType.MANAGER,
            CompanyType.DEVELOPER);
        Assignment newDeveloper = assignmentQueryPort.findByProjectIdAndUserId(id,
            command.getDeveloperId());
        if (!oldDeveloper.getUser().getId().equals(newDeveloper.getUser().getId())) {
            oldDeveloper.toMember();
            newDeveloper.toManager();
        }

        Assignment oldClient = assignmentQueryPort.findByProjectIdAndUserTypeAndCompanyType(id,
            UserType.MANAGER,
            CompanyType.CLIENT);
        Assignment newClient = assignmentQueryPort.findByProjectIdAndUserId(id,
            command.getClientId());
        if (!oldClient.getUser().getId().equals(newClient.getUser().getId())) {
            oldClient.toMember();
            newClient.toManager();
        }

        saveProjectUserPort.save(oldDeveloper);
        saveProjectUserPort.save(newDeveloper);
        saveProjectUserPort.save(oldClient);
        saveProjectUserPort.save(newClient);

    }

    // todo: if 조건 검증도 도메인에서 하라고 헀던거 같음.
    @Override
    public void changedStatus(Long projectId) {
        Project project = readProjectPort.findProjectById(projectId);

        if (project.getStatus() == ProjectStatus.IN_PROGRESS) {
            project.statusCompleted();
        } else {
            project.statusInProgress();
        }
        port.update(project);
    }
}
