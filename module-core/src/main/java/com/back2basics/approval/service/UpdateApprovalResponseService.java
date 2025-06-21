package com.back2basics.approval.service;

import com.back2basics.approval.model.ApprovalRequest;
import com.back2basics.approval.model.ApprovalResponse;
import com.back2basics.approval.port.in.UpdateApprovalResponseUseCase;
import com.back2basics.approval.port.in.command.CreateApprovalCommand;
import com.back2basics.approval.port.in.command.UpdateApprovalCommand;
import com.back2basics.approval.port.out.ApprovalRequestCommandPort;
import com.back2basics.approval.port.out.ApprovalRequestQueryPort;
import com.back2basics.approval.port.out.ApprovalResponseCommandPort;
import com.back2basics.approval.port.out.ApprovalResponseQueryPort;
import com.back2basics.infra.validation.validator.ApprovalValidator;
import com.back2basics.infra.validation.validator.UserValidator;
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

    @Override
    public void change(Long responseId, Long userId, UpdateApprovalCommand command) {
        approvalValidator.validateApproval(responseId, userId);

        ApprovalResponse approvalResponse = approvalResponseQueryPort.findById(responseId);
        approvalResponse.updateStatus(command.message(), command.status());
        approvalResponseCommandPort.update(approvalResponse);
    }

    @Override
    public void addApprover(Long requestId, Long userId, CreateApprovalCommand command) {
        userValidator.validateAllUsersExist(command.responseIds());
        // todo request, userId validation
        ApprovalRequest approvalRequest = approvalRequestQueryPort.findById(requestId);
        command.responseIds().forEach(approvalRequest::addResponse);

        approvalRequestCommandPort.update(approvalRequest);
    }
}
