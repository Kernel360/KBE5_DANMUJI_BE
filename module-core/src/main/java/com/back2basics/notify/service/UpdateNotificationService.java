package com.back2basics.notify.service;

import com.back2basics.notify.model.Notification;
import com.back2basics.notify.port.in.UpdateNotificationUseCase;
import com.back2basics.notify.port.out.NotificationDeletePort;
import com.back2basics.notify.port.out.NotificationQueryPort;
import com.back2basics.notify.port.out.NotificationSavePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateNotificationService implements UpdateNotificationUseCase {

    private final NotificationQueryPort notificationQueryPort;
    private final NotificationSavePort notificationSavePort;
    private final NotificationDeletePort notificationDeletePort;

    @Override
    public void toggleRead(Long notificationId) {
        Notification notification = notificationQueryPort.findById(notificationId);
        notification.toggleRead();
        notificationSavePort.save(notification);
    }

    @Override
    public void markAllAsRead(Long clientId) {
        List<Notification> notifications = notificationQueryPort.findByClientIdAndIsReadFalse(
            clientId);
        notifications.forEach(Notification::markAsRead);
        notificationSavePort.saveAll(notifications);
    }

    @Override
    public void deleteById(Long notificationId) {
        notificationDeletePort.softDeleteById(notificationId);
    }
}
