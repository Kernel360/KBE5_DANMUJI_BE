package com.back2basics.projectstep.port.in;

import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;

public interface CreateProjectStepUseCase {
    // todo: 프로젝트 단계 추가
    void createStep(Long projectId);
}
