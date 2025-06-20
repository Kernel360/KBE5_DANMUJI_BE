package com.back2basics.approval.service;

import com.back2basics.approval.model.ApprovalResponse;
import com.back2basics.approval.port.in.UpdateApprovalResponseUseCase;
import com.back2basics.approval.port.in.command.UpdateApprovalCommand;
import com.back2basics.approval.port.out.ApprovalRequestCommandPort;
import com.back2basics.approval.port.out.ApprovalResponseCommandPort;
import com.back2basics.approval.port.out.ApprovalResponseQueryPort;
import com.back2basics.infra.validation.validator.ApprovalValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateApprovalResponseService implements UpdateApprovalResponseUseCase {

    private final ApprovalValidator approvalValidator;
    private final ApprovalRequestCommandPort approvalRequestCommandPort;
    private final ApprovalResponseQueryPort approvalResponseQueryPort;
    private final ApprovalResponseCommandPort approvalResponseCommandPort;

    @Override
    public void change(Long responseId, Long userId, UpdateApprovalCommand command) {
        approvalValidator.validateApproval(responseId, userId);
        ApprovalResponse approvalResponse = approvalResponseQueryPort.findById(responseId);
        approvalResponse.updateStatus(command.message(), command.status());
        approvalResponseCommandPort.update(approvalResponse);
    }
}
