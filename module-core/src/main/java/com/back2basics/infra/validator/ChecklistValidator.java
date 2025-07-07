package com.back2basics.infra.validator;

import static com.back2basics.infra.exception.checklist.ChecklistErrorCode.ACCESS_DENIED;
import static com.back2basics.infra.exception.checklist.ChecklistErrorCode.CHECKLIST_NOT_FOUND;

import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.out.ChecklistQueryPort;
import com.back2basics.infra.exception.checklist.ChecklistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChecklistValidator {

    private final ChecklistQueryPort checklistQueryPort;

    public void validateNotFoundChecklistId(Long checklistId) {
        boolean exists = checklistQueryPort.existsById(checklistId);
        if (!exists) {
            throw new ChecklistException(CHECKLIST_NOT_FOUND);
        }
    }

    public void validateChecklistCreator(Long checklistId, Long userId) {
        Checklist checklist = checklistQueryPort.findById(checklistId);
        if (!checklist.getUserId().equals(userId)) {
            throw new ChecklistException(ACCESS_DENIED);
        }
    }
}
