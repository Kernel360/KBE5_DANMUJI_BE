package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.DeleteChecklistUseCase;
import com.back2basics.checklist.port.out.ChecklistCommandPort;
import com.back2basics.checklist.port.out.ChecklistQueryPort;
import com.back2basics.infra.validator.ChecklistValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteChecklistService implements DeleteChecklistUseCase {

    private final ChecklistQueryPort checklistQueryPort;
    private final ChecklistCommandPort checklistCommandPort;
    private final ChecklistValidator checklistValidator;

    @Override
    public void delete(Long userId, Long checklistId) {
        checklistValidator.validateChecklistCreator(checklistId, userId);
        Checklist checklist = checklistQueryPort.findById(checklistId);
        checklist.delete();
        checklistCommandPort.delete(checklist);
    }
}
