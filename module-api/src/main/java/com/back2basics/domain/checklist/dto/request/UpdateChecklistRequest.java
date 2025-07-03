package com.back2basics.domain.checklist.dto.request;

import com.back2basics.checklist.port.in.command.UpdateChecklistCommand;

public record UpdateChecklistRequest(String title, String content) {

    public UpdateChecklistCommand toCommand() {
        return new UpdateChecklistCommand(title, content);
    }
}
