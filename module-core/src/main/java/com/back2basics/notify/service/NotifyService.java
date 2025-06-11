package com.back2basics.notify.service;

import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.notify.model.Notification;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.out.NotificationCommandPort;
import com.back2basics.notify.util.SseEmitterUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class NotifyService implements NotifyUseCase {

    private final UserValidator userValidator;
    private final NotificationCommandPort notificationCommandPort;
    private final SseEmitterUtil sseEmitterUtil;

    @Override
    public void notify(Long clientId, String message, NotificationType type) {
        userValidator.validateNotFoundUserId(clientId);

        Notification notification = Notification.create(clientId, message, type);
        notificationCommandPort.save(notification);

        SseEmitter emitter = sseEmitterUtil.get(clientId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                    .name("ALERT")
                    .data(notification));
            } catch (IOException e) {
                emitter.completeWithError(e);
                sseEmitterUtil.remove(clientId);
            }
        }
    }
}
