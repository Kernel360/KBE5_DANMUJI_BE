package com.back2basics.notify.port.in;

public interface UpdateNotificationUseCase {

    void markAsRead(Long notificationId);

    void markAllAsRead(Long clientId);
}
