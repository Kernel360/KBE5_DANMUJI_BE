package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.UpdateChecklistUseCase;
import com.back2basics.checklist.port.in.command.UpdateChecklistApprovalCommand;
import com.back2basics.checklist.port.in.command.UpdateChecklistCommand;
import com.back2basics.checklist.port.out.ChecklistCommandPort;
import com.back2basics.checklist.port.out.ChecklistQueryPort;
import com.back2basics.infra.validator.ChecklistValidator;
import com.back2basics.infra.validator.UserValidator;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateChecklistService implements UpdateChecklistUseCase {

    private final ChecklistQueryPort checklistQueryPort;
    private final ChecklistCommandPort checklistCommandPort;
    private final ReadProjectStepPort readProjectStepPort;
    private final UserValidator userValidator;
    private final ChecklistValidator checklistValidator;
    private final NotifyUseCase notifyUseCase;

    @Override
    public void update(Long checklistId, Long userId, UpdateChecklistCommand command) {
        checklistValidator.validateChecklistCreator(checklistId, userId);

        Checklist checklist = checklistQueryPort.findById(checklistId);
        checklist.update(command.title(), command.content());
        checklistCommandPort.update(checklist);

    }

    @Override
    public void addApproval(Long checklistId, Long userId, UpdateChecklistApprovalCommand command) {
        userValidator.validateAllUsersExist(command.approvalIds());
        checklistValidator.validateChecklistCreator(checklistId, userId);
        Checklist checklist = checklistQueryPort.findById(checklistId);
        command.approvalIds().forEach(checklist::addResponse);

        checklistCommandPort.update(checklist);
        ProjectStep projectStep = readProjectStepPort.findById(checklist.getProjectStepId());

        for (Long clientId : command.approvalIds()) {
            SendNotificationCommand notifyCommand = new SendNotificationCommand(
                clientId,
                projectStep.getProjectId(),
                checklistId,
                NotificationType.CHECKLIST_REQUEST.getDescription(),
                NotificationType.CHECKLIST_REQUEST
            );
            notifyUseCase.notify(notifyCommand);
        }
    }
}
