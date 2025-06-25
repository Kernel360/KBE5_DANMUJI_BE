package com.back2basics.projectstep.service;

import static com.back2basics.infra.exception.projectstep.ProjectStepErrorCode.STEP_NOT_FOUND;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.exception.projectstep.ProjectStepException;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.in.UpdateProjectStepUseCase;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateProjectStepService implements UpdateProjectStepUseCase {


    private final ReadProjectStepPort readProjectStepPort;
    private final SaveProjectStepPort saveProjectStepPort;
    private final ProjectValidator projectValidator;
    private final HistoryLogService historyLogService;

    @Override
    public void updateStepName(UpdateProjectStepCommand command, Long stepId, Long loggedInUserId) {
        ProjectStep step = readProjectStepPort.findById(stepId);
        ProjectStep before = ProjectStep.copyOf(step);
        step.updateName(command.getName());
        ProjectStep updatedStep = saveProjectStepPort.save(step);

        historyLogService.logUpdated(DomainType.STEP, loggedInUserId, before, updatedStep,
            "프로젝트 단계 수정");

    }

    @Override
    public void updateApprovalStatus(ProjectStepStatus projectStepStatus, Long stepId,
        Long loggedInUserId) {
        ProjectStep step = readProjectStepPort.findById(stepId);
        ProjectStep before = ProjectStep.copyOf(step);
        step.updateStatus(projectStepStatus);
        ProjectStep updatedStep = saveProjectStepPort.save(step);

        historyLogService.logUpdated(DomainType.STEP, loggedInUserId, before, updatedStep,
            "프로젝트 단계 승인요청 변경");
    }


    // todo
    //  swlee : 이력 생성 보류 -> 어떤 작업 해주는 기능인가요??
    @Override
    public void reorderSteps(Long projectId, List<Long> stepIdsInNewOrder) {
        projectValidator.findById(projectId);
        List<ProjectStep> steps = readProjectStepPort.findAllById(stepIdsInNewOrder);

        Map<Long, ProjectStep> stepMap = steps.stream()
            .collect(Collectors.toMap(ProjectStep::getStepId, s -> s));
        for (int i = 0; i < stepIdsInNewOrder.size(); i++) {
            Long stepId = stepIdsInNewOrder.get(i);
            ProjectStep step = stepMap.get(stepId);

            if (step == null) {
                throw new ProjectStepException(STEP_NOT_FOUND);
            }

            step.updateStepOrder(i + 1);
        }

        saveProjectStepPort.saveAll(steps);
    }

    @Override
    public void updateStepStatus(Long stepId) {
        ProjectStep projectStep = readProjectStepPort.findById(stepId);
        projectStep.updateStepStatus(projectStep);
        saveProjectStepPort.save(projectStep);
    }

    @Override
    public void revertStepStatus(Long stepId) {
        ProjectStep projectStep = readProjectStepPort.findById(stepId);
        projectStep.revertStepStatus();
        saveProjectStepPort.save(projectStep);
    }
}
