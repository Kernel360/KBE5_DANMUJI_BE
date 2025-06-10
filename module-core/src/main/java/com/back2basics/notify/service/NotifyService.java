package com.back2basics.notify.service;

import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.notify.model.Notification;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.out.NotificationCommandPort;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class NotifyService implements NotifyUseCase {

    private final Map<Long, SseEmitter> clients = new ConcurrentHashMap<>();
    private final UserValidator userValidator;
    private final NotificationCommandPort notificationCommandPort;

    @Override
    public void notify(Long clientId, String message, NotificationType type) {
        userValidator.validateNotFoundUserId(clientId);

        Notification notification = Notification.create(clientId, message, type);

        notificationCommandPort.save(notification);

        SseEmitter emitter = clients.get(clientId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                    .name("ALERT")
                    .data(notification));
            } catch (IOException e) {
                emitter.completeWithError(e);
                clients.remove(clientId);
            }
        }
    }
}
