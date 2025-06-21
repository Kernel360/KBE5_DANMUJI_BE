package com.back2basics.domain.approval.dto.request;

import com.back2basics.approval.model.ApprovalResponseStatus;
import com.back2basics.approval.port.in.command.UpdateApprovalCommand;

public record UpdateApprovalRequest(String message, ApprovalResponseStatus status) {

    public UpdateApprovalCommand toCommand() {
        return new UpdateApprovalCommand(message, status);
    }
}
