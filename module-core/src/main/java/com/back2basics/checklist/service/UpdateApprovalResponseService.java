package com.back2basics.checklist.service;

import com.back2basics.checklist.model.ApprovalResponse;
import com.back2basics.checklist.model.ApprovalResponseStatus;
import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.UpdateApprovalResponseUseCase;
import com.back2basics.checklist.port.in.command.CreateApprovalCommand;
import com.back2basics.checklist.port.in.command.UpdateApprovalCommand;
import com.back2basics.checklist.port.out.ApprovalRequestCommandPort;
import com.back2basics.checklist.port.out.ApprovalRequestQueryPort;
import com.back2basics.checklist.port.out.ApprovalResponseCommandPort;
import com.back2basics.checklist.port.out.ApprovalResponseQueryPort;
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
public class UpdateApprovalResponseService implements UpdateApprovalResponseUseCase {

    private final ApprovalValidator approvalValidator;
    private final UserValidator userValidator;
    private final ApprovalRequestCommandPort approvalRequestCommandPort;
    private final ApprovalRequestQueryPort approvalRequestQueryPort;
    private final ApprovalResponseQueryPort approvalResponseQueryPort;
    private final ApprovalResponseCommandPort approvalResponseCommandPort;
    private final NotifyUseCase notifyUseCase;
    private final HistoryLogService historyLogService;

    @Override
    public void change(Long responseId, Long userId, UpdateApprovalCommand command) {
        approvalValidator.validateApproval(responseId, userId);

        ApprovalResponse approvalResponse = approvalResponseQueryPort.findById(responseId);
        approvalResponse.updateStatus(command.message(), command.status());
        approvalResponseCommandPort.update(approvalResponse);

        Checklist checklist = approvalRequestQueryPort.findById(
            approvalResponse.getApprovalRequestId());
        Checklist before = Checklist.copyOf(checklist);

        SendNotificationCommand notifyCommand = getSendNotificationCommand(
            command, checklist, approvalResponse);
        notifyUseCase.notify(notifyCommand);

        if (command.status().equals(ApprovalResponseStatus.REJECTED)) {
            checklist.reject();
            approvalRequestCommandPort.save(checklist);

            historyLogService.logUpdated(DomainType.APPROVAL_REQUEST, userId, before,
                checklist, "승인 요청 거부");
        } else {
            List<ApprovalResponse> responseList = approvalResponseQueryPort.findResponsesByRequestId(
                checklist.getId());
            boolean allResponded = responseList.stream()
                .allMatch(r -> r.getStatus() != ApprovalResponseStatus.PENDING);

            if (allResponded) {
                checklist.approve();
                approvalRequestCommandPort.save(checklist);

                historyLogService.logUpdated(DomainType.APPROVAL_REQUEST, userId, before,
                    checklist, "승인 요청 수락");
            }
        }

    }

    private static SendNotificationCommand getSendNotificationCommand(UpdateApprovalCommand command,
        Checklist checklist, ApprovalResponse approvalResponse) {
        NotificationType type = command.status().equals(ApprovalResponseStatus.REJECTED)
            ? NotificationType.STEP_APPROVAL_REJECTED : NotificationType.STEP_APPROVAL_ACCEPTED;

        return new SendNotificationCommand(
            checklist.getUserId(),
            approvalResponse.getApprovalRequestId(),
            null, // todo
            type.getDescription(),
            type
        );
    }

    @Override
    public void addApprover(Long requestId, Long userId, CreateApprovalCommand command) {
        userValidator.validateAllUsersExist(command.responseIds());
        // todo request, userId validation
        Checklist checklist = approvalRequestQueryPort.findById(requestId);
        command.responseIds().forEach(checklist::addResponse);

        approvalRequestCommandPort.update(checklist);

        for (Long clientId : command.responseIds()) {
            SendNotificationCommand notifyCommand = new SendNotificationCommand(
                clientId,
                requestId,
                null, // todo
                NotificationType.STEP_APPROVAL_REQUEST.getDescription(),
                NotificationType.STEP_APPROVAL_REQUEST
            );
            notifyUseCase.notify(notifyCommand);
        }
    }
}
