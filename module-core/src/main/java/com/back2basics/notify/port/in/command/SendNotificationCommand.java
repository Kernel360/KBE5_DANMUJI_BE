package com.back2basics.notify.port.in.command;

import com.back2basics.notify.model.Notification;
import com.back2basics.notify.model.NotificationType;

public record SendNotificationCommand(Long clientId, Long projectId, Long postId, String message,
                                      NotificationType type) {

    public Notification toEntity() {
        return Notification.create(clientId, projectId, postId, message, type);
    }
}
