package com.back2basics.approval.port.in;

import com.back2basics.approval.port.in.command.CreateApprovalCommand;
import com.back2basics.approval.port.in.command.UpdateApprovalCommand;

public interface UpdateApprovalResponseUseCase {

    void change(Long responseId, Long userId, UpdateApprovalCommand command);

    void addApprover(Long requestId, Long id, CreateApprovalCommand command);
}
