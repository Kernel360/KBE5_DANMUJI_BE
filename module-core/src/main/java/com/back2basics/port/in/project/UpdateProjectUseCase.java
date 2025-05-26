package com.back2basics.port.in.project;

import com.back2basics.service.project.dto.ProjectResponseDto;
import com.back2basics.service.project.dto.ProjectUpdateCommand;

public interface UpdateProjectUseCase {

    ProjectResponseDto updateProject(Long projectId, ProjectUpdateCommand projectUpdateCommand);
}
