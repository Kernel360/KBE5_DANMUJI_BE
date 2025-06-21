package com.back2basics.approval.service;

import com.back2basics.approval.port.in.ReadApprovalUseCase;
import com.back2basics.approval.port.out.ApprovalResponseQueryPort;
import com.back2basics.approval.service.result.ApproverIdsResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadApprovalService implements ReadApprovalUseCase {

    private final ApprovalResponseQueryPort approvalResponseQueryPort;

    @Override
    public ApproverIdsResult findApproverIdsByRequestId(Long requestId) {
        List<Long> ids = approvalResponseQueryPort.findApproverIdsByRequestId(requestId);
        return new ApproverIdsResult(ids);
    }
}
