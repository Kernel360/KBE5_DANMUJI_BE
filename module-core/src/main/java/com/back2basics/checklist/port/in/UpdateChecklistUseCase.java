package com.back2basics.checklist.port.in;

import com.back2basics.checklist.port.in.command.UpdateChecklistApprovalCommand;
import com.back2basics.checklist.port.in.command.UpdateChecklistCommand;

public interface UpdateChecklistUseCase {

    void update(Long checklistId, Long userId, UpdateChecklistCommand command);

    void addApproval(Long checklistId, Long userId, UpdateChecklistApprovalCommand command);
}
