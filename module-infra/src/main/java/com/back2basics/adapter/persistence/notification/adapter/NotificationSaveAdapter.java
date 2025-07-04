package com.back2basics.adapter.persistence.notification.adapter;

import com.back2basics.adapter.persistence.notification.NotificationEntity;
import com.back2basics.adapter.persistence.notification.NotificationEntityRepository;
import com.back2basics.adapter.persistence.notification.NotificationMapper;
import com.back2basics.notify.model.Notification;
import com.back2basics.notify.port.out.NotificationSavePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSaveAdapter implements NotificationSavePort {

    private final NotificationEntityRepository notificationEntityRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = notificationMapper.toEntity(notification);
        return notificationMapper.toDomain(notificationEntityRepository.save(entity));
    }

    @Override
    public void saveAll(List<Notification> notifications) {
        List<NotificationEntity> entities = notifications.stream()
            .map(notificationMapper::toEntity)
            .toList();
        notificationEntityRepository.saveAll(entities);
    }

}
