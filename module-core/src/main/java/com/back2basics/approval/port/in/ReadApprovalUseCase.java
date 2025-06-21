package com.back2basics.approval.port.in;

import com.back2basics.approval.service.result.ApproverIdsResult;

public interface ReadApprovalUseCase {

    ApproverIdsResult findApproverIdsByRequestId(Long requestId);

}
