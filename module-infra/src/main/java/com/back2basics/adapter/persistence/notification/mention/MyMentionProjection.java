package com.back2basics.adapter.persistence.notification.mention;

import com.back2basics.notify.model.NotificationType;
import java.time.LocalDateTime;

public record MyMentionProjection(
    Long id,
    Long clientId,
    Long referenceId,
    String content,
    NotificationType type,
    Boolean isRead,
    LocalDateTime createdAt
) {}
