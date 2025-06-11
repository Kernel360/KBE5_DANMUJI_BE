package com.back2basics.notify.port.out;

public interface NotificationDeletePort {

    void softDeleteById(Long notificationId);
}

