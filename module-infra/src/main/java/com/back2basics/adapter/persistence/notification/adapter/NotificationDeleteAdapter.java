package com.back2basics.adapter.persistence.notification.adapter;

import static com.back2basics.infra.exception.notification.NotificationErrorCode.NOTIFICATION_NOT_FOUND;

import com.back2basics.adapter.persistence.notification.NotificationEntity;
import com.back2basics.adapter.persistence.notification.NotificationEntityRepository;
import com.back2basics.infra.exception.notification.NotificationException;
import com.back2basics.notify.port.out.NotificationDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NotificationDeleteAdapter implements NotificationDeletePort {

    private final NotificationEntityRepository notificationEntityRepository;

    @Override
    @Transactional
    public void softDeleteById(Long notificationId) {
        NotificationEntity notificationEntity = notificationEntityRepository.findById(
            notificationId).orElseThrow(() -> new NotificationException(NOTIFICATION_NOT_FOUND));

        notificationEntity.markDeleted();
    }
}
