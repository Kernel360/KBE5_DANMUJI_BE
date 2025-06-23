package com.back2basics.projectstep.port.out;

import com.back2basics.projectstep.model.ProjectStep;
import java.util.List;

public interface ReadProjectStepPort {

    ProjectStep findById(Long stepId);

    List<ProjectStep> findAllByProjectId(Long projectId);

    List<ProjectStep> findAllById(List<Long> ids);

    Integer findMaxStepOrderByProjectId(Long projectId);

    int totalCompletedStep(Long projectId);

    int totalStep(Long projectId);
}
