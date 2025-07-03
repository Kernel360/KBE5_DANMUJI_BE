package com.back2basics.checklist.port.in;

import com.back2basics.checklist.port.in.command.CreateChecklistCommand;
import com.back2basics.checklist.port.in.command.UpdateApprovalCommand;

public interface UpdateApprovalUseCase {

    void change(Long responseId, Long userId, UpdateApprovalCommand command);

    void addApprover(Long requestId, Long userId, CreateChecklistCommand command);
}
