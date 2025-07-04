package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.port.in.DeleteApprovalUseCase;
import com.back2basics.checklist.port.out.ApprovalCommandPort;
import com.back2basics.checklist.port.out.ApprovalQueryPort;
import com.back2basics.infra.validator.ApprovalValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteApprovalService implements DeleteApprovalUseCase {

    private final ApprovalValidator approvalValidator;
    private final ApprovalQueryPort approvalQueryPort;
    private final ApprovalCommandPort approvalCommandPort;

    @Override
    public void delete(Long userId, Long approvalId) {
        approvalValidator.validateApproval(approvalId, userId);
        Approval approval = approvalQueryPort.findById(approvalId);
        approval.delete();
        approvalCommandPort.delete(approval);
    }
}
