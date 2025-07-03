package com.back2basics.domain.checklist.dto.request;

import com.back2basics.checklist.port.in.command.CreateApprovalCommand;
import java.util.List;

public record CreateChecklistRequest(String title, String content, List<Long> responseIds) {

    public CreateApprovalCommand toCommand() {
        return new CreateApprovalCommand(this.title, this.content, this.responseIds);
    }
}
