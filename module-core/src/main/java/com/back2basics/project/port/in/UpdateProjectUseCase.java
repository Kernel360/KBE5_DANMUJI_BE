package com.back2basics.project.port.in;

import com.back2basics.project.port.in.command.ProjectUpdateCommand;

public interface UpdateProjectUseCase {

    void updateProject(Long projectId, ProjectUpdateCommand projectUpdateCommand,
        Long loggedInUserId);
    
    void changedStatus(Long projectId, Long loggedInUserId);

    void calculateProgressRate(Long projectId);

    void calculateProgressRateByDeleteStep(Long projectId);
}
