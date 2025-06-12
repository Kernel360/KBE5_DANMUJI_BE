package com.back2basics.projectstep.port.in;

import com.back2basics.projectstep.service.result.DetailProjectStepResult;
import com.back2basics.projectstep.service.result.ProjectStepSimpleResult;
import com.back2basics.projectstep.service.result.ReadProjectStepResult;
import java.util.List;

public interface ReadProjectStepUseCase {
    List<ReadProjectStepResult> findAll();

    List<ProjectStepSimpleResult> findByProjectId(Long projectId);

    List<DetailProjectStepResult> findDetailByProjectId(Long projectId);

    ReadProjectStepResult findById(Long stepId);
}
