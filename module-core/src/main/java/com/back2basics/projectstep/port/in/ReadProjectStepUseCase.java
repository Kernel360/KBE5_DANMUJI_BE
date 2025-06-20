package com.back2basics.projectstep.port.in;

import com.back2basics.projectstep.service.result.ProjectStepResult;
import java.util.List;

public interface ReadProjectStepUseCase {

    List<ProjectStepResult> findByProjectId(Long projectId);

    ProjectStepResult findById(Long stepId);
}
