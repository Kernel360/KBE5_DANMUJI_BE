package com.back2basics.project.port.in;

import com.back2basics.project.port.in.command.ProjectResponseDto;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;

public interface UpdateProjectUseCase {

    ProjectResponseDto updateProject(Long projectId, ProjectUpdateCommand projectUpdateCommand);

    ProjectResponseDto changedStatus(Long projectId);
}
