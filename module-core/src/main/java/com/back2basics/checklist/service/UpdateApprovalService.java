package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.model.ApprovalStatus;
import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.UpdateApprovalUseCase;
import com.back2basics.checklist.port.in.command.UpdateApprovalCommand;
import com.back2basics.checklist.port.in.command.UpdateChecklistApprovalCommand;
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
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
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
    private final ReadProjectStepPort readProjectStepPort;

    @Override
    public void change(Long approvalId, Long userId, UpdateApprovalCommand command) {
        approvalValidator.validateApproval(approvalId, userId);

        Approval approval = approvalQueryPort.findById(approvalId);
        approval.updateStatus(command.message(), command.status());
        approvalCommandPort.update(approval);

        Checklist checklist = checklistQueryPort.findById(
            approval.getApprovalRequestId());
        Checklist before = Checklist.copyOf(checklist);

        ProjectStep projectStep = readProjectStepPort.findById(checklist.getProjectStepId());

        SendNotificationCommand notifyCommand = getSendNotificationCommand(
            command, checklist, projectStep.getProjectId());
        notifyUseCase.notify(notifyCommand);

        if (command.status().equals(ApprovalStatus.REJECTED)) {
            checklist.reject();
            checklistCommandPort.save(checklist);

            historyLogService.logUpdated(DomainType.CHECKLIST, userId, before,
                checklist, "체크리스트 승인 요청 거부");
        } else {
            List<Approval> responseList = approvalQueryPort.findResponsesByRequestId(
                checklist.getId());
            boolean allResponded = responseList.stream()
                .allMatch(r -> r.getStatus() != ApprovalStatus.PENDING);

            if (allResponded) {
                checklist.approve();
                checklistCommandPort.save(checklist);

                historyLogService.logUpdated(DomainType.CHECKLIST, userId, before,
                    checklist, "체크리스트 승인 요청 수락");
            }
        }

    }

    private static SendNotificationCommand getSendNotificationCommand(UpdateApprovalCommand command,
        Checklist checklist, Long projectId) {
        NotificationType type = command.status().equals(ApprovalStatus.REJECTED)
            ? NotificationType.CHECKLIST_REJECTED : NotificationType.CHECKLIST_ACCEPTED;

        return new SendNotificationCommand(
            checklist.getUserId(),
            projectId,
            checklist.getId(),
            type.getDescription(),
            type
        );
    }

    @Override
    public void addApproval(Long checklistId, Long userId, UpdateChecklistApprovalCommand command) {
        userValidator.validateAllUsersExist(command.approvalIds());
        // todo request, userId validation
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
