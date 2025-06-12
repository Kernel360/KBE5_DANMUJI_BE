package com.back2basics.domain.notify.dto.response;

import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.service.result.NotificationResult;
import java.time.LocalDateTime;

public record NotificationResponse(Long id, Long clientId, String message, NotificationType type,
                                   Boolean isRead, LocalDateTime createdAt, LocalDateTime updatedAt,
                                   LocalDateTime deletedAt) {

    public static NotificationResponse from(NotificationResult result) {
        return new NotificationResponse(
            result.id(),
            result.clientId(),
            result.message(),
            result.type(),
            result.isRead() != null ? result.isRead() : false,
            result.createdAt(),
            result.updatedAt(),
            result.deletedAt()
        );
    }

}
