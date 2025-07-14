package com.back2basics.domain.checklist.dto.request;

import com.back2basics.checklist.model.ApprovalStatus;
import com.back2basics.checklist.port.in.command.UpdateApprovalCommand;
import com.back2basics.custom.CustomStringLengthCheck;
import jakarta.validation.constraints.NotBlank;

public record UpdateApprovalRequest(

    @NotBlank(message = "반려 내용은 필수입니다.")
    @CustomStringLengthCheck(min = 1, max = 100, message = "제목은 1자 이상 100자 이하여야 합니다.")
    String message,
    ApprovalStatus status) {

    public UpdateApprovalCommand toCommand() {
        return new UpdateApprovalCommand(message, status);
    }
}
