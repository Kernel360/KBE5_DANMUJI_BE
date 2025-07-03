package com.back2basics.checklist.port.in;

import com.back2basics.checklist.service.result.ApprovalResult;
import com.back2basics.checklist.service.result.ChecklistInfoResult;
import java.util.List;

public interface ReadApprovalUseCase {

    ChecklistInfoResult findByRequestId(Long requestId);

    List<ChecklistInfoResult> findAll();

    List<ApprovalResult> findResponsesByRequestId(Long requestId);

    ApprovalResult findByResponseId(Long responseId);
}
