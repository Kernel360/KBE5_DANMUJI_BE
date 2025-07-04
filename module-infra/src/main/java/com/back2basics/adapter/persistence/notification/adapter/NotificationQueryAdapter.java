package com.back2basics.adapter.persistence.notification.adapter;

import static com.back2basics.infra.exception.notification.NotificationErrorCode.NOTIFICATION_NOT_FOUND;

import com.back2basics.adapter.persistence.notification.NotificationEntityRepository;
import com.back2basics.adapter.persistence.notification.NotificationMapper;
import com.back2basics.infra.exception.notification.NotificationException;
import com.back2basics.infra.validator.UserValidator;
import com.back2basics.notify.model.Notification;
import com.back2basics.notify.port.out.NotificationQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationQueryAdapter implements NotificationQueryPort {

    private final UserValidator userValidator;
    private final NotificationEntityRepository notificationEntityRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Notification findById(Long id) {
        return notificationEntityRepository.findById(id).map(notificationMapper::toDomain)
            .orElseThrow(() -> new NotificationException(NOTIFICATION_NOT_FOUND));
    }

    @Override
    public Page<Notification> findByClientId(Long clientId, Pageable pageable) {
        userValidator.validateNotFoundUserId(clientId);
        return notificationEntityRepository.findByClientIdAndDeletedAtIsNull(clientId, pageable)
            .map(notificationMapper::toDomain);
    }

    @Override
    public List<Notification> findByClientIdAndIsReadFalse(Long clientId) {
        userValidator.validateNotFoundUserId(clientId);
        return notificationEntityRepository.findByClientIdAndIsReadFalseAndDeletedAtIsNull(clientId)
            .stream()
            .map(notificationMapper::toDomain)
            .toList();
    }

    @Override
    public long countUnreadByClientId(Long clientId) {
        userValidator.validateNotFoundUserId(clientId);
        return notificationEntityRepository.countByClientIdAndIsReadFalseAndDeletedAtIsNull(
            clientId);
    }

}
