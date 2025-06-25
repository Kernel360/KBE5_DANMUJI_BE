package com.back2basics.projectstep.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
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
    private final HistoryLogService historyLogService;

    // save는 생성할 때 연관관계 부여하는데 삭제는 이미 연관관계 설정되어 있는 애들이라 다른 save를 만들어야 할 지 고민
    // todo: step order 순서 변경 처리
    @Override
    public void softDelete(Long stepId, Long loggedInUserId) {
        ProjectStep step = readPort.findById(stepId);
        step.softDelete();
        ProjectStep deletedStep = savePort.save(step);

        historyLogService.logDeleted(DomainType.STEP, loggedInUserId, deletedStep, "프로젝트 단계 비활성화");
    }
}
