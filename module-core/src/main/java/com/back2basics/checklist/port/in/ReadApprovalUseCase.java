package com.back2basics.checklist.port.in;

import com.back2basics.checklist.service.result.ApprovalResult;
import com.back2basics.checklist.service.result.ChecklistDetailResult;

public interface ReadApprovalUseCase {

    ChecklistDetailResult findAllByChecklistId(Long checklistId);

    ApprovalResult findById(Long id);
}
