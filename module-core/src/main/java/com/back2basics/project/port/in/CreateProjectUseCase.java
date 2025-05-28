package com.back2basics.project.port.in;

import com.back2basics.service.project.dto.ProjectCreateCommand;

public interface CreateProjectUseCase {

    void createProject(ProjectCreateCommand projectCreateCommand);
}
