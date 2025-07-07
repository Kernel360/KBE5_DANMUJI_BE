package com.back2basics.domain.checklist.dto.request;

import com.back2basics.checklist.port.in.command.CreateChecklistCommand;
import java.util.List;

public record CreateChecklistRequest(String title, String content, List<Long> approvalIds) {

    public CreateChecklistCommand toCommand() {
        return new CreateChecklistCommand(this.title, this.content, this.approvalIds);
    }
}
