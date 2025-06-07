package com.back2basics.projectstep.port.in;

import com.back2basics.projectstep.service.result.ReadProjectStepResult;
import java.util.List;

public interface ReadProjectStepUseCase {
    List<ReadProjectStepResult> findAll();
}
