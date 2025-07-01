package com.back2basics.notify.service;

import com.back2basics.notify.model.Notification;
import com.back2basics.notify.port.in.NotificationQueryUseCase;
import com.back2basics.notify.port.out.NotificationQueryPort;
import com.back2basics.notify.service.result.NotificationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationQueryService implements NotificationQueryUseCase {

    private final NotificationQueryPort notificationQueryPort;

    @Override
    public Page<NotificationResult> findByClientId(Long clientId, Pageable pageable) {
        Page<Notification> notifications = notificationQueryPort.findByClientId(clientId, pageable);
        return notifications.map(NotificationResult::from);
    }

    @Override
    public long countUnreadByClientId(Long clientId) {
        return notificationQueryPort.countUnreadByClientId(clientId);
    }
}
