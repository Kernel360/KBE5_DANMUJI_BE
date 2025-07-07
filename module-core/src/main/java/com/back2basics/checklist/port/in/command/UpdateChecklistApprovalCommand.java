package com.back2basics.checklist.port.in.command;

import java.util.List;

public record UpdateChecklistApprovalCommand(List<Long> approvalIds) {

}
