package com.back2basics.approval.port.in;

import com.back2basics.approval.service.result.ApprovalInfoResult;
import com.back2basics.approval.service.result.ApproverResult;
import java.util.List;

public interface ReadApprovalUseCase {

    ApprovalInfoResult findByRequestId(Long requestId);

    List<ApprovalInfoResult> findAll();

    List<ApproverResult> findResponsesByRequestId(Long requestId);

    ApproverResult findByResponseId(Long responseId);
}
