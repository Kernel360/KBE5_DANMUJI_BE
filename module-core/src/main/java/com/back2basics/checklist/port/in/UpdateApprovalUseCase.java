package com.back2basics.checklist.port.in;

import com.back2basics.checklist.port.in.command.UpdateApprovalCommand;
import com.back2basics.checklist.port.in.command.UpdateChecklistApprovalCommand;

public interface UpdateApprovalUseCase {

    void change(Long approvalId, Long userId, UpdateApprovalCommand command);

    void addApproval(Long checklistId, Long userId, UpdateChecklistApprovalCommand command);
}
