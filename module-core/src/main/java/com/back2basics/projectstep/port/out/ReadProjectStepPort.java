package com.back2basics.projectstep.port.out;

import com.back2basics.projectstep.model.ProjectStep;
import java.util.List;

public interface ReadProjectStepPort {
    List<ProjectStep> findAllByProjectId(Long projectId);
}
