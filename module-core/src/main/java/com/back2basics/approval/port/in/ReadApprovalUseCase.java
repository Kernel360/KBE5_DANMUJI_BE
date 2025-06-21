package com.back2basics.approval.port.in;

import com.back2basics.approval.service.result.ApprovalInfoResult;
import java.util.List;

public interface ReadApprovalUseCase {

    ApprovalInfoResult findByRequestId(Long requestId);

    List<ApprovalInfoResult> findAll();
}
