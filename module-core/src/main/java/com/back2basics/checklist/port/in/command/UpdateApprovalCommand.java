package com.back2basics.checklist.port.in.command;

import com.back2basics.checklist.model.ApprovalResponseStatus;

public record UpdateApprovalCommand(String message, ApprovalResponseStatus status) {

}
