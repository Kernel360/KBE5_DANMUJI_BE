package com.back2basics.notify.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Notification {

    private final Long id;
    private final Long clientId;
    private final Long referenceId;
    private final String message;
    private Boolean isRead;
    private final NotificationType type;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    @Builder
    public Notification(Long id, Long clientId, Long referenceId, String message, Boolean isRead,
        NotificationType type, LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt) {
        this.id = id;
        this.clientId = clientId;
        this.referenceId = referenceId;
        this.message = message;
        this.isRead = isRead;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Notification create(Long clientId, Long referenceId, String message,
        NotificationType type) {
        return Notification.builder()
            .clientId(clientId)
            .referenceId(referenceId)
            .message(message)
            .isRead(false)
            .type(type)
            .build();
    }

    public void markAsRead() {
        this.isRead = true;
    }

}
