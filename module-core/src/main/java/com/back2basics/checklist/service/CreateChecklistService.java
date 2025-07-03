package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.CreateChecklistUseCase;
import com.back2basics.checklist.port.in.command.CreateChecklistCommand;
import com.back2basics.checklist.port.out.ChecklistCommandPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.validation.validator.ProjectStepValidator;
import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.notify.port.in.NotifyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateChecklistService implements CreateChecklistUseCase {

    private final UserValidator userValidator; // validation 처리는 service에서 처리해야 함
    private final ProjectStepValidator projectStepValidator;
    private final ChecklistCommandPort checklistCommandPort;
    private final NotifyUseCase notifyUseCase;
    private final HistoryLogService historyLogService;

    @Override
    public void create(Long stepId, Long userId, CreateChecklistCommand command) {
        projectStepValidator.validateNotFoundStepId(stepId);
        userValidator.validateNotFoundUserId(userId);
        userValidator.validateAllUsersExist(command.responseIds());

        Checklist checklist = Checklist.create(stepId, userId, command.title(),
            command.content(), command.responseIds());
        Checklist savedChecklist = checklistCommandPort.create(checklist);

        // todo
//        for (Long clientId : command.responseIds()) {
//            SendNotificationCommand notifyCommand = new SendNotificationCommand(
//                clientId,
//                savedApprovalRequest.getId(),
//                NotificationType.STEP_APPROVAL_REQUEST.getDescription(),
//                NotificationType.STEP_APPROVAL_REQUEST
//            );
//            notifyUseCase.notify(notifyCommand);
//        }

        historyLogService.logCreated(DomainType.CHECKLIST, userId, savedChecklist,
            "체크리스트 생성");
    }
}
