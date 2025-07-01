package com.back2basics.domain.notify.dto.response;

import com.back2basics.notify.service.result.NotificationResult;
import java.time.LocalDateTime;

public record NotificationResponse(Long id, Long clientId, Long projectId, Long postId,
                                   String message,
                                   String type, Boolean isRead, LocalDateTime createdAt,
                                   LocalDateTime updatedAt, LocalDateTime deletedAt) {

    public static NotificationResponse from(NotificationResult result) {
        return new NotificationResponse(
            result.id(),
            result.clientId(),
            result.projectId(),
            result.postId(),
            result.message(),
            result.type(),
            result.isRead(),
            result.createdAt(),
            result.updatedAt(),
            result.deletedAt()
        );
    }

}
