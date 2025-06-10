package com.back2basics.notify.service;

import com.back2basics.notify.model.Notification;
import com.back2basics.notify.port.in.UpdateNotificationUseCase;
import com.back2basics.notify.port.out.NotificationCommandPort;
import com.back2basics.notify.port.out.NotificationQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateNotificationService implements UpdateNotificationUseCase {

    private final NotificationQueryPort notificationQueryPort;
    private final NotificationCommandPort notificationCommandPort;

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationQueryPort.findById(notificationId);
        notification.markAsRead();
        notificationCommandPort.save(notification);
    }

    @Override
    public void markAllAsRead(Long clientId) {
        List<Notification> notifications = notificationQueryPort.findByClientIdAndIsReadFalse(
            clientId);
        notifications.forEach(Notification::markAsRead);
        notificationCommandPort.saveAll(notifications);
    }
}
