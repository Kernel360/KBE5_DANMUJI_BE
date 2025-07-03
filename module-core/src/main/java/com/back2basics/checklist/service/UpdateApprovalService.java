package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.model.ApprovalStatus;
import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.UpdateApprovalUseCase;
import com.back2basics.checklist.port.in.command.CreateChecklistCommand;
import com.back2basics.checklist.port.in.command.UpdateApprovalCommand;
import com.back2basics.checklist.port.out.ApprovalCommandPort;
import com.back2basics.checklist.port.out.ApprovalQueryPort;
import com.back2basics.checklist.port.out.ChecklistCommandPort;
import com.back2basics.checklist.port.out.ChecklistQueryPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.validation.validator.ApprovalValidator;
import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateApprovalService implements UpdateApprovalUseCase {

    private final ApprovalValidator approvalValidator;
    private final UserValidator userValidator;
    private final ChecklistCommandPort checklistCommandPort;
    private final ChecklistQueryPort checklistQueryPort;
    private final ApprovalQueryPort approvalQueryPort;
    private final ApprovalCommandPort approvalCommandPort;
    private final NotifyUseCase notifyUseCase;
    private final HistoryLogService historyLogService;

    @Override
    public void change(Long responseId, Long userId, UpdateApprovalCommand command) {
        approvalValidator.validateApproval(responseId, userId);

        Approval approval = approvalQueryPort.findById(responseId);
        approval.updateStatus(command.message(), command.status());
        approvalCommandPort.update(approval);

        Checklist checklist = checklistQueryPort.findById(
            approval.getApprovalRequestId());
        Checklist before = Checklist.copyOf(checklist);

        SendNotificationCommand notifyCommand = getSendNotificationCommand(
            command, checklist, approval);
        notifyUseCase.notify(notifyCommand);

        if (command.status().equals(ApprovalStatus.REJECTED)) {
            checklist.reject();
            checklistCommandPort.save(checklist);

            historyLogService.logUpdated(DomainType.CHECKLIST, userId, before,
                checklist, "승인 요청 거부");
        } else {
            List<Approval> responseList = approvalQueryPort.findResponsesByRequestId(
                checklist.getId());
            boolean allResponded = responseList.stream()
                .allMatch(r -> r.getStatus() != ApprovalStatus.PENDING);

            if (allResponded) {
                checklist.approve();
                checklistCommandPort.save(checklist);

                historyLogService.logUpdated(DomainType.CHECKLIST, userId, before,
                    checklist, "승인 요청 수락");
            }
        }

    }

    private static SendNotificationCommand getSendNotificationCommand(UpdateApprovalCommand command,
        Checklist checklist, Approval approval) {
        NotificationType type = command.status().equals(ApprovalStatus.REJECTED)
            ? NotificationType.CHECKLIST_REJECTED : NotificationType.CHECKLIST_ACCEPTED;

        return new SendNotificationCommand(
            checklist.getUserId(),
            approval.getApprovalRequestId(),
            null, // todo
            type.getDescription(),
            type
        );
    }

    @Override
    public void addApprover(Long requestId, Long userId, CreateChecklistCommand command) {
        userValidator.validateAllUsersExist(command.responseIds());
        // todo request, userId validation
        Checklist checklist = checklistQueryPort.findById(requestId);
        command.responseIds().forEach(checklist::addResponse);

        checklistCommandPort.update(checklist);

        for (Long clientId : command.responseIds()) {
            SendNotificationCommand notifyCommand = new SendNotificationCommand(
                clientId,
                requestId,
                null, // todo
                NotificationType.CHECKLIST_REQUEST.getDescription(),
                NotificationType.CHECKLIST_REQUEST
            );
            notifyUseCase.notify(notifyCommand);
        }
    }
}
