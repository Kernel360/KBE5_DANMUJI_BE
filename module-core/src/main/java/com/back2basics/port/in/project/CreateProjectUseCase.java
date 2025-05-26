package com.back2basics.port.in.project;

import com.back2basics.service.project.dto.ProjectCreateCommand;

public interface CreateProjectUseCase {
    void createProject(ProjectCreateCommand projectCreateCommand);
}
