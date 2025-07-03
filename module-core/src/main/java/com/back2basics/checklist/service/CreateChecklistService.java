package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.CreateChecklistUseCase;
import com.back2basics.checklist.port.in.command.CreateChecklistCommand;
import com.back2basics.checklist.port.out.ChecklistCommandPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateChecklistService implements CreateChecklistUseCase {

    private final UserValidator userValidator; // validation 처리는 service에서 처리해야 함
    private final ReadProjectStepPort readProjectStepPort;
    private final ChecklistCommandPort checklistCommandPort;
    private final NotifyUseCase notifyUseCase;
    private final HistoryLogService historyLogService;

    @Override
    public void create(Long stepId, Long userId, CreateChecklistCommand command) {
        ProjectStep projectStep = readProjectStepPort.findById(stepId);
        Long projectId = projectStep.getProjectId();
        userValidator.validateNotFoundUserId(userId);
        userValidator.validateAllUsersExist(command.responseIds());

        Checklist checklist = Checklist.create(stepId, userId, command.title(),
            command.content(), command.responseIds());
        Checklist savedChecklist = checklistCommandPort.create(checklist);

        // todo
        for (Long clientId : command.responseIds()) {
            SendNotificationCommand notifyCommand = new SendNotificationCommand(
                clientId,
                projectId,
                savedChecklist.getId(),
                NotificationType.CHECKLIST_REQUEST.getDescription(),
                NotificationType.CHECKLIST_REQUEST
            );
            notifyUseCase.notify(notifyCommand);
        }

        historyLogService.logCreated(DomainType.CHECKLIST, userId, savedChecklist,
            "체크리스트 생성");
    }
}
