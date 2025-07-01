package com.back2basics.notify.port.out;

import com.back2basics.notify.model.Notification;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationQueryPort {

    Notification findById(Long id);

    Page<Notification> findByClientId(Long clientId, Pageable pageable);

    List<Notification> findByClientIdAndIsReadFalse(Long clientId);

    long countUnreadByClientId(Long clientId);
}
