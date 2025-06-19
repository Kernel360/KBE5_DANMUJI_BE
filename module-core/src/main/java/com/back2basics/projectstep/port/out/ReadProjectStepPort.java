package com.back2basics.projectstep.port.out;

import com.back2basics.projectstep.model.ProjectStep;
import java.util.List;

public interface ReadProjectStepPort {

    ProjectStep findById(Long stepId);

    // todo: 중복 메서드 제거
    List<ProjectStep> findAllByProjectId(Long projectId);

    List<ProjectStep> findByProjectId(Long projectId);
}
