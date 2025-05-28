package com.back2basics.project.port.in;

import com.back2basics.service.project.dto.ProjectResponseDto;
import com.back2basics.service.project.dto.ProjectUpdateCommand;

public interface UpdateProjectUseCase {

    ProjectResponseDto updateProject(Long projectId, ProjectUpdateCommand projectUpdateCommand);
}
