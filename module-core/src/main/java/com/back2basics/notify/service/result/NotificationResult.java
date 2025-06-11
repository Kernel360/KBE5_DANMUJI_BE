package com.back2basics.notify.service.result;

import com.back2basics.notify.model.Notification;
import com.back2basics.notify.model.NotificationType;
import java.time.LocalDateTime;

public record NotificationResult(Long id, Long clientId, Long referenceId, String message,
                                 NotificationType type,
                                 Boolean isRead, LocalDateTime createdAt, LocalDateTime updatedAt,
                                 LocalDateTime deletedAt) {

    public static NotificationResult from(Notification notification) {
        return new NotificationResult(
            notification.getId(),
            notification.getClientId(),
            notification.getReferenceId(),
            notification.getMessage(),
            notification.getType(),
            notification.getIsRead() != null ? notification.getIsRead() : false,
            notification.getCreatedAt(),
            notification.getUpdatedAt(),
            notification.getDeletedAt()
        );
    }
}
