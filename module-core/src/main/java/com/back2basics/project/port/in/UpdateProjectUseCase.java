package com.back2basics.project.port.in;

import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.project.service.result.ProjectUpdateResult;

public interface UpdateProjectUseCase {

    ProjectUpdateResult updateProject(Long projectId, ProjectUpdateCommand projectUpdateCommand);

    ProjectUpdateResult changedStatus(Long projectId);
}
