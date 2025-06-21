package com.back2basics.approval.port.in;

import com.back2basics.approval.service.result.ApprovalInfoResult;

public interface ReadApprovalUseCase {

    ApprovalInfoResult findByRequestId(Long requestId);

}
