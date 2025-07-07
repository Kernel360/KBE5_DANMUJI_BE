package com.back2basics.checklist.port.in;

import com.back2basics.checklist.port.in.command.UpdateApprovalCommand;

public interface UpdateApprovalUseCase {

    void update(Long approvalId, Long userId, UpdateApprovalCommand command);

}
