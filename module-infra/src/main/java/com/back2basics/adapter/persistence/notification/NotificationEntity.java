package com.back2basics.adapter.persistence.notification;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.notify.model.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notifications")
public class NotificationEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @Column(name = "type", nullable = false)
    private NotificationType type;

    @Builder
    public NotificationEntity(Long id, Long clientId, String message, NotificationType type,
        Boolean isRead) {
        this.id = id;
        this.clientId = clientId;
        this.message = message;
        this.type = type;
        this.isRead = isRead;
    }

}
