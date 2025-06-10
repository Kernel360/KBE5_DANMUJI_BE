package com.back2basics.notify.service.result;

import com.back2basics.notify.model.Notification;
import com.back2basics.notify.model.NotificationType;
import java.time.LocalDateTime;

public record NotificationResult(Long id, Long clientId, String message, NotificationType type,
                                 Boolean isRead, LocalDateTime createdAt) {

    public static NotificationResult from(Notification notification) {
        return new NotificationResult(
            notification.getId(),
            notification.getClientId(),
            notification.getMessage(),
            notification.getType(),
            notification.getIsRead() != null ? notification.getIsRead() : false,
            notification.getCreatedAt()
        );
    }
}
