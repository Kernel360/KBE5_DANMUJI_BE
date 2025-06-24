package com.back2basics.domain.checklist.dto.request;

import com.back2basics.checklist.port.in.command.CreateCheckListCommand;

public record CreateCheckListRequest(String content) {

    public CreateCheckListCommand toCommand() {
        return new CreateCheckListCommand(this.content);
    }
}
