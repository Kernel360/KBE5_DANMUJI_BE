package com.back2basics.checklist.port.in;

import com.back2basics.checklist.service.result.ApprovalInfoResult;
import com.back2basics.checklist.service.result.ApproverResult;
import java.util.List;

public interface ReadApprovalUseCase {

    ApprovalInfoResult findByRequestId(Long requestId);

    List<ApprovalInfoResult> findAll();

    List<ApproverResult> findResponsesByRequestId(Long requestId);

    ApproverResult findByResponseId(Long responseId);
}
