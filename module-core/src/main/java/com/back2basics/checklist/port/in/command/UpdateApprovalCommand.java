package com.back2basics.checklist.port.in.command;

import com.back2basics.checklist.model.ApprovalStatus;

public record UpdateApprovalCommand(String message, ApprovalStatus status) {

}
