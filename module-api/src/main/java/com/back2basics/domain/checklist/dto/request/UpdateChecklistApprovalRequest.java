package com.back2basics.domain.checklist.dto.request;

import com.back2basics.checklist.port.in.command.UpdateChecklistApprovalCommand;
import java.util.List;

public record UpdateChecklistApprovalRequest(List<Long> approvalIds) {

    public UpdateChecklistApprovalCommand toCommand() {
        return new UpdateChecklistApprovalCommand(this.approvalIds);
    }
}
