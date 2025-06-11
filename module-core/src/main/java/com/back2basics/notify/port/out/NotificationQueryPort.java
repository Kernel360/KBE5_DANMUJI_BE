package com.back2basics.notify.port.out;

import com.back2basics.notify.model.Notification;
import java.util.List;

public interface NotificationQueryPort {

    Notification findById(Long id);

    List<Notification> findByClientId(Long clientId);

    List<Notification> findByClientIdAndIsReadFalse(Long clientId);

    long countUnreadByClientId(Long clientId);
}
