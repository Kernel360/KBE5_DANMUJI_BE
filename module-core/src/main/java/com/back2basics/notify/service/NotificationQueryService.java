package com.back2basics.notify.service;

import com.back2basics.notify.model.Notification;
import com.back2basics.notify.port.in.NotificationQueryUseCase;
import com.back2basics.notify.port.out.NotificationQueryPort;
import com.back2basics.notify.service.result.NotificationResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationQueryService implements NotificationQueryUseCase {

    private final NotificationQueryPort notificationQueryPort;

    @Override
    public List<NotificationResult> findByClientId(Long clientId) {
        List<Notification> notifications = notificationQueryPort.findByClientId(clientId);
        return notifications.stream()
            .map(NotificationResult::from)
            .toList();
    }
}
