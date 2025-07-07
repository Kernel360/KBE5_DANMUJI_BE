package com.back2basics.checklist.port.in;

import com.back2basics.checklist.port.in.command.CreateChecklistCommand;

public interface CreateChecklistUseCase {

    void create(Long stepId, Long userId, CreateChecklistCommand command);
}
