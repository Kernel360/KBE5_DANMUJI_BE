package com.back2basics.notify.service.result;

import com.back2basics.notify.model.Notification;
import java.time.LocalDateTime;

public record NotificationResult(Long id, Long clientId, Long referenceId, String message,
                                 String type, Boolean isRead, LocalDateTime createdAt,
                                 LocalDateTime updatedAt, LocalDateTime deletedAt) {

    public static NotificationResult from(Notification notification) {
        return new NotificationResult(
            notification.getId(),
            notification.getClientId(),
            notification.getReferenceId(),
            notification.getType().getDescription(),
            notification.getType().name(),
            notification.getIsRead(),
            notification.getCreatedAt(),
            notification.getUpdatedAt(),
            notification.getDeletedAt()
        );
    }
}
