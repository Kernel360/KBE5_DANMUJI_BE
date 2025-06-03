package com.back2basics.projectstep.service;

import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.in.DeleteProjectStepUseCase;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProjectStepService implements DeleteProjectStepUseCase {

    private final SaveProjectStepPort savePort;
    private final ReadProjectStepPort readPort;

    // save는 생성할 때 연관관계 부여하는데 삭제는 이미 연관관계 설정되어 있는 애들이라 다른 save를 만들어야 할 지 고민
    @Override
    public void softDelete(Long stepId) {
        ProjectStep step = readPort.findById(stepId);
        step.softDelete();
        savePort.save(step);
    }
}
