package com.back2basics.checklist.port.in;

import com.back2basics.checklist.service.result.ChecklistWithApprovalResult;
import java.util.List;

public interface ReadApprovalUseCase {

    List<ChecklistWithApprovalResult> findAllByChecklistId(Long checklistId);

    ChecklistWithApprovalResult findById(Long id);
}
