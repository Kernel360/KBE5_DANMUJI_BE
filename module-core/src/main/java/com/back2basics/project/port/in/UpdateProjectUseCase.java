package com.back2basics.project.port.in;

import com.back2basics.project.port.in.command.ProjectUpdateCommand;

public interface UpdateProjectUseCase {

    void updateProject(Long projectId, ProjectUpdateCommand projectUpdateCommand);

    void changedStatus(Long projectId);

    void calculateProgressRate(Long projectId);
}
