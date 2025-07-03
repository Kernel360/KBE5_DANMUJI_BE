package com.back2basics.checklist.port.in;

import com.back2basics.checklist.service.result.ApprovalResult;
import java.util.List;

public interface ReadApprovalUseCase {

    List<ApprovalResult> findResponsesByRequestId(Long requestId);

    ApprovalResult findByResponseId(Long responseId);
}
