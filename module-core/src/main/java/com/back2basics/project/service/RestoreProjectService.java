package com.back2basics.project.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.validator.ProjectValidator;
import com.back2basics.infra.validator.UserValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.RestoreProjectUseCase;
import com.back2basics.project.port.out.RestoreProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestoreProjectService implements RestoreProjectUseCase {

    private final ProjectValidator projectValidator;
    private final HistoryLogService historyLogService;
    private final UserValidator userValidator;
    private final RestoreProjectPort restoreProjectPort;

    @Override
    public void restoreProject(Long requesterId, Long projectId) {
        userValidator.isAdmin(requesterId);
        Project project = projectValidator.findProjectForRestore(projectId);

        project.restore();
        restoreProjectPort.restoreProject(project);

        historyLogService.logRestored(DomainType.PROJECT, requesterId, project, "비활성화 프로젝트 복구");
    }
}
