package com.back2basics.checklist.port.in;

import com.back2basics.checklist.port.in.command.CreateApprovalCommand;
import com.back2basics.checklist.port.in.command.UpdateApprovalCommand;

public interface UpdateApprovalResponseUseCase {

    void change(Long responseId, Long userId, UpdateApprovalCommand command);

    void addApprover(Long requestId, Long userId, CreateApprovalCommand command);
}
