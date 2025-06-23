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
import com.back2basics.infra.validation.validator.ApprovalValidator;
import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
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

    @Override
    public void change(Long responseId, Long userId, UpdateApprovalCommand command) {
        approvalValidator.validateApproval(responseId, userId);

        ApprovalResponse approvalResponse = approvalResponseQueryPort.findById(responseId);
        approvalResponse.updateStatus(command.message(), command.status());
        approvalResponseCommandPort.update(approvalResponse);

        ApprovalRequest approvalRequest = approvalRequestQueryPort.findById(
            approvalResponse.getApprovalRequestId());

        SendNotificationCommand notifyCommand = getSendNotificationCommand(
            command, approvalRequest, approvalResponse);
        notifyUseCase.notify(notifyCommand);
    }

    private static SendNotificationCommand getSendNotificationCommand(UpdateApprovalCommand command,
        ApprovalRequest approvalRequest, ApprovalResponse approvalResponse) {
        String msg = command.status().equals(ApprovalResponseStatus.REJECTED) ? "단계 승인 요청이 거절되었습니다."
            : "단계 승인이 완료되었습니다";

        NotificationType type = command.status().equals(ApprovalResponseStatus.REJECTED)
            ? NotificationType.STEP_APPROVAL_REJECTED : NotificationType.STEP_APPROVAL_ACCEPTED;

        return new SendNotificationCommand(
            approvalRequest.getRequesterId(),
            approvalResponse.getApprovalRequestId(),
            msg,
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
                "새로운 승인 요청이 도착했습니다.",
                NotificationType.STEP_APPROVAL_REQUEST
            );
            notifyUseCase.notify(notifyCommand);
        }
    }
}
