package com.back2basics.notify.service;

import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import com.back2basics.notify.util.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyService implements NotifyUseCase {

    private final UserValidator userValidator;
    private final NotificationPublisher publisher;

    @Async
    @Override
    public void notify(SendNotificationCommand command) {
        userValidator.validateNotFoundUserId(command.clientId());
        publisher.publish(command);
    }
}
