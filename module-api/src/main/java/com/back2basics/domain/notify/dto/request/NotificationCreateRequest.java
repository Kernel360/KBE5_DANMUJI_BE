package com.back2basics.domain.notify.dto.request;

import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.command.SendNotificationCommand;

public record NotificationCreateRequest(Long clientId, Long projectId, Long referenceId,
                                        String message, NotificationType type) {

    public SendNotificationCommand toCommand() {
        return new SendNotificationCommand(clientId, projectId, referenceId, message, type);
    }
}
