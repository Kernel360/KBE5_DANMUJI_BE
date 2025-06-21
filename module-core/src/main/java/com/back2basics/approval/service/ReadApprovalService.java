package com.back2basics.approval.service;

import com.back2basics.approval.model.ApprovalRequest;
import com.back2basics.approval.port.in.ReadApprovalUseCase;
import com.back2basics.approval.port.out.ApprovalRequestQueryPort;
import com.back2basics.approval.port.out.ApprovalResponseQueryPort;
import com.back2basics.approval.service.result.ApprovalInfoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadApprovalService implements ReadApprovalUseCase {

    private final ApprovalRequestQueryPort approvalRequestQueryPort;
    private final ApprovalResponseQueryPort approvalResponseQueryPort;

    @Override
    public ApprovalInfoResult findByRequestId(Long requestId) {
        ApprovalRequest request = approvalRequestQueryPort.findById(requestId);
        return new ApprovalInfoResult(request.getId(), request.getProjectStepId(),
            request.getRequesterId(), request.getApprovalRequestStatus(), request.getRequestedAt());
    }
}
