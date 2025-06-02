package com.back2basics.projectstep.port.out;

import com.back2basics.projectstep.model.ProjectStep;
import java.util.List;
import java.util.Optional;

public interface ReadProjectStepPort {

    ProjectStep findById(Long stepId);

    List<ProjectStep> findAllByProjectId(Long projectId);
}
