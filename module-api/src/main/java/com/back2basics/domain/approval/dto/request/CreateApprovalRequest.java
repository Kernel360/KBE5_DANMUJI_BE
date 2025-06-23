package com.back2basics.domain.approval.dto.request;

import com.back2basics.approval.port.in.command.CreateApprovalCommand;
import java.util.List;

public record CreateApprovalRequest(List<Long> responseIds) {

    public CreateApprovalCommand toCommand() {
        return new CreateApprovalCommand(this.responseIds);
    }
}
