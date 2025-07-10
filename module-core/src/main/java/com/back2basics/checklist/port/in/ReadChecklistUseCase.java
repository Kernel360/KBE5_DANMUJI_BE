package com.back2basics.checklist.port.in;

import com.back2basics.checklist.service.result.ChecklistInfoResult;
import java.util.List;

public interface ReadChecklistUseCase {

    ChecklistInfoResult findByChecklistId(Long checklistId);

    List<ChecklistInfoResult> findAll();

    List<ChecklistInfoResult> findAllByStepId(Long stepId);
}
