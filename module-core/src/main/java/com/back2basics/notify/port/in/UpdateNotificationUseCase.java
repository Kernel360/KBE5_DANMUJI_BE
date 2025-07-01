package com.back2basics.notify.port.in;

public interface UpdateNotificationUseCase {

    void toggleRead(Long notificationId);

    void markAllAsRead(Long clientId);

    void deleteById(Long notificationId);
}
