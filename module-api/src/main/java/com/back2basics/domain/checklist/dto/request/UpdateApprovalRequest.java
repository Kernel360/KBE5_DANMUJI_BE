package com.back2basics.domain.checklist.dto.request;

import com.back2basics.checklist.model.ApprovalResponseStatus;
import com.back2basics.checklist.port.in.command.UpdateApprovalCommand;

public record UpdateApprovalRequest(String message, ApprovalResponseStatus status) {

    public UpdateApprovalCommand toCommand() {
        return new UpdateApprovalCommand(message, status);
    }
}
