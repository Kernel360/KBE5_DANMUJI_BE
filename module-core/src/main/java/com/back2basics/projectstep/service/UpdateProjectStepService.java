package com.back2basics.projectstep.service;

import static com.back2basics.infra.exception.projectstep.ProjectStepErrorCode.STEP_NOT_FOUND;

import com.back2basics.infra.exception.projectstep.ProjectStepException;
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

    private final ReadProjectStepPort readPort;
    private final SaveProjectStepPort savePort;

    @Override
    public void updateStepName(UpdateProjectStepCommand command, Long stepId) {
        ProjectStep step = readPort.findById(stepId);
        step.updateName(command.getName());
        savePort.save(step);
    }

    @Override
    public void updateApprovalStatus(ProjectStepStatus projectStepStatus,
        Long stepId) {
        ProjectStep step = readPort.findById(stepId);
        step.updateStatus(projectStepStatus);
        savePort.save(step);
    }

    @Override
    public void reorderSteps(Long projectId, List<Long> stepIdsInNewOrder) {
        List<ProjectStep> steps = readPort.findAllById(stepIdsInNewOrder);

        Map<Long, ProjectStep> stepMap = steps.stream()
            .collect(Collectors.toMap(ProjectStep::getId, s -> s));
        for (int i = 0; i < stepIdsInNewOrder.size(); i++) {
            Long stepId = stepIdsInNewOrder.get(i);
            ProjectStep step = stepMap.get(stepId);

            if (step == null) {
                throw new ProjectStepException(STEP_NOT_FOUND);
            }

            step.updateStepOrder(i + 1);
        }

        savePort.saveAll(steps);
    }
}
