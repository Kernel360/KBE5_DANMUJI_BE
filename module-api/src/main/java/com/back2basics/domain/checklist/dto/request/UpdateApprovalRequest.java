package com.back2basics.domain.checklist.dto.request;

import com.back2basics.checklist.model.ApprovalStatus;
import com.back2basics.checklist.port.in.command.UpdateApprovalCommand;
import com.back2basics.custom.CustomStringLengthCheck;

public record UpdateApprovalRequest(

    @CustomStringLengthCheck(min = 0, max = 100, message = "반려 메세지는 100자 이하여야 합니다.")
    String message,
    ApprovalStatus status) {

    public UpdateApprovalCommand toCommand() {
        return new UpdateApprovalCommand(message, status);
    }
}
