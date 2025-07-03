package com.back2basics.checklist.port.in;

import com.back2basics.checklist.port.in.command.CreateApprovalCommand;

public interface CreateApprovalRequestUseCase {

    void create(Long stepId, Long requesterId, CreateApprovalCommand command);
}
