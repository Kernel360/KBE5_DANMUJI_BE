package com.back2basics.notify.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Notification {

    private final Long id;
    private final Long clientId;
    private final Long projectId;
    private final Long postId;
    private final String message;
    private Boolean isRead;
    private final NotificationType type;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    @Builder
    public Notification(Long id, Long clientId, Long projectId, Long postId, String message,
        Boolean isRead,
        NotificationType type, LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt) {
        this.id = id;
        this.clientId = clientId;
        this.projectId = projectId;
        this.postId = postId;
        this.message = message;
        this.isRead = isRead;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Notification create(Long clientId, Long projectId, Long postId, String message,
        NotificationType type) {
        return Notification.builder()
            .clientId(clientId)
            .projectId(projectId)
            .postId(postId)
            .message(message)
            .isRead(false)
            .type(type)
            .build();
    }

    public void markAsRead() {
        if (!this.isRead) {
            this.isRead = true;
        }
    }

    public void toggleRead() {
        this.isRead = !isRead;
    }
}
