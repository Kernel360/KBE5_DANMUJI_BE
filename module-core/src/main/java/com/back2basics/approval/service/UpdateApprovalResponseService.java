package com.back2basics.approval.service;

import com.back2basics.approval.model.ApprovalRequest;
import com.back2basics.approval.model.ApprovalResponse;
import com.back2basics.approval.model.ApprovalResponseStatus;
import com.back2basics.approval.port.in.UpdateApprovalResponseUseCase;
import com.back2basics.approval.port.in.command.CreateApprovalCommand;
import com.back2basics.approval.port.in.command.UpdateApprovalCommand;
import com.back2basics.approval.port.out.ApprovalRequestCommandPort;
import com.back2basics.approval.port.out.ApprovalRequestQueryPort;
import com.back2basics.approval.port.out.ApprovalResponseCommandPort;
import com.back2basics.approval.port.out.ApprovalResponseQueryPort;
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

        ApprovalRequest approvalRequest = approvalRequestQueryPort.findById(
            approvalResponse.getApprovalRequestId());
        ApprovalRequest before = ApprovalRequest.copyOf(approvalRequest);

        SendNotificationCommand notifyCommand = getSendNotificationCommand(
            command, approvalRequest, approvalResponse);
        notifyUseCase.notify(notifyCommand);

        if (command.status().equals(ApprovalResponseStatus.REJECTED)) {
            approvalRequest.reject();
            approvalRequestCommandPort.save(approvalRequest);

            historyLogService.logUpdated(DomainType.APPROVAL_REQUEST, userId, before,
                approvalRequest, "승인 요청 거부");
        } else {
            List<ApprovalResponse> responseList = approvalResponseQueryPort.findResponsesByRequestId(
                approvalRequest.getId());
            boolean allResponded = responseList.stream()
                .allMatch(r -> r.getStatus() != ApprovalResponseStatus.PENDING);

            if (allResponded) {
                approvalRequest.approve();
                approvalRequestCommandPort.save(approvalRequest);

                historyLogService.logUpdated(DomainType.APPROVAL_REQUEST, userId, before,
                    approvalRequest, "승인 요청 수락");
            }
        }

    }

    private static SendNotificationCommand getSendNotificationCommand(UpdateApprovalCommand command,
        ApprovalRequest approvalRequest, ApprovalResponse approvalResponse) {
        NotificationType type = command.status().equals(ApprovalResponseStatus.REJECTED)
            ? NotificationType.STEP_APPROVAL_REJECTED : NotificationType.STEP_APPROVAL_ACCEPTED;

        return new SendNotificationCommand(
            approvalRequest.getRequesterId(),
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
        ApprovalRequest approvalRequest = approvalRequestQueryPort.findById(requestId);
        command.responseIds().forEach(approvalRequest::addResponse);

        approvalRequestCommandPort.update(approvalRequest);

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
