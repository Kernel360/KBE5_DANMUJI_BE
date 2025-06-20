package com.back2basics.approval.port.in;

import com.back2basics.approval.port.in.command.UpdateApprovalCommand;

public interface UpdateApprovalResponseUseCase {

    void change(Long responseId, Long userId, UpdateApprovalCommand command);
}
