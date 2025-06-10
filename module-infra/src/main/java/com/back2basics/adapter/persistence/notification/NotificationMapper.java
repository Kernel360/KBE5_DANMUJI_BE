package com.back2basics.adapter.persistence.notification;

import com.back2basics.notify.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    public Notification toDomain(NotificationEntity entity) {
        return Notification.builder()
            .id(entity.getId())
            .clientId(entity.getClientId())
            .message(entity.getMessage())
            .isRead(entity.getIsRead())
            .type(entity.getType())
            .createdAt(entity.getCreatedAt())
            .build();
    }

    public NotificationEntity toEntity(Notification notification) {
        return NotificationEntity.builder()
            .id(notification.getId())
            .clientId(notification.getClientId())
            .message(notification.getMessage())
            .type(notification.getType())
            .isRead(notification.getIsRead() != null ? notification.getIsRead() : false)
            .createdAt(notification.getCreatedAt())
            .build();
    }
}
