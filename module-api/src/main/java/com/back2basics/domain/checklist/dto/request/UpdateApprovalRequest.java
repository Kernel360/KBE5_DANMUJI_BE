package com.back2basics.domain.checklist.dto.request;

import com.back2basics.checklist.model.ApprovalStatus;
import com.back2basics.checklist.port.in.command.UpdateApprovalCommand;

public record UpdateApprovalRequest(String message, ApprovalStatus status) {

    public UpdateApprovalCommand toCommand() {
        return new UpdateApprovalCommand(message, status);
    }
}
