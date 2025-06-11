package com.back2basics.notify.service;

import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import com.back2basics.notify.util.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyService implements NotifyUseCase {

    private final UserValidator userValidator;
    private final NotificationPublisher publisher;

    @Override
    public void notify(Long clientId, String message, NotificationType type) {
        userValidator.validateNotFoundUserId(clientId);
        SendNotificationCommand command = new SendNotificationCommand(clientId, message, type);
        publisher.publish(command);
    }
}
