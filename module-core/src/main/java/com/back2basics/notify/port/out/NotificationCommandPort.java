package com.back2basics.notify.port.out;

import com.back2basics.notify.model.Notification;

public interface NotificationCommandPort {

    Notification save(Notification notification);

}
