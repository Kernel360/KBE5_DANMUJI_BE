package com.back2basics.notify.port.out;

import com.back2basics.notify.model.Notification;
import java.util.List;

public interface NotificationSavePort {

    Notification save(Notification notification);

    void saveAll(List<Notification> notifications);
}
