package com.back2basics.checklist.port.in;

public interface DeleteChecklistUseCase {

    void delete(Long userId, Long checklistId);
}
