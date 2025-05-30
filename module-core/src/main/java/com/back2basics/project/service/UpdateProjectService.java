package com.back2basics.project.service;

import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.project.port.out.UpdateProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectService implements UpdateProjectUseCase {

    private final UpdateProjectPort port;
    private final ProjectValidator projectValidator;


    // todo : 사용자 인증 로직 추가
    @Override
    public void updateProject(Long id,
        ProjectUpdateCommand projectUpdateCommand) {
        Project project = projectValidator.findProjectById(id);
        project.update(projectUpdateCommand);
        port.update(project);
    }

    @Override
    public void changedStatus(Long projectId) {
        Project project = projectValidator.findProjectById(projectId);

        if (project.getStatus() == ProjectStatus.IN_PROGRESS) {
            project.statusCompleted();
        } else {
            project.statusInProgress();
        }
        port.update(project);
    }
}
