package com.back2basics.checklist.port.out;

import com.back2basics.checklist.model.Checklist;
import java.util.List;

public interface ChecklistQueryPort {

    Checklist findById(Long requestId);

    List<Checklist> findAll();

    boolean existsById(Long checklistId);

    List<Checklist> findAllByStepId(Long stepId);
}
