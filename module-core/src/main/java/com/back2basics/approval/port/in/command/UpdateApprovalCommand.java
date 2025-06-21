package com.back2basics.approval.port.in.command;

import com.back2basics.approval.model.ApprovalResponseStatus;

public record UpdateApprovalCommand(String message, ApprovalResponseStatus status) {

}
