package com.back2basics.notify.service;

import com.back2basics.notify.model.Notification;
import com.back2basics.notify.port.in.SubscribeNotificationUseCase;
import com.back2basics.notify.port.out.NotificationQueryPort;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class SubscribeNotificationService implements SubscribeNotificationUseCase {

    private final NotificationQueryPort notificationQueryPort;

    private final Map<Long, SseEmitter> clients = new ConcurrentHashMap<>();

    @Override
    public SseEmitter subscribe(Long clientId) {
        SseEmitter emitter = new SseEmitter(60 * 1000L);
        clients.put(clientId, emitter);

        emitter.onCompletion(() -> clients.remove(clientId));
        emitter.onTimeout(() -> clients.remove(clientId));
        emitter.onError(e -> clients.remove(clientId));

        try {
            emitter.send(SseEmitter.event()
                .name("CONNECTED")
                .data("SSE connection established"));

            // 읽지 않은 알림 전송
            List<Notification> unread = notificationQueryPort.findByClientIdAndIsReadFalse(
                clientId);
            for (Notification notification : unread) {
                emitter.send(SseEmitter.event()
                    .name("ALERT")
                    .data(notification));
            }

        } catch (IOException e) {
            emitter.completeWithError(e);
            clients.remove(clientId);
        }

        return emitter;
    }

    public SseEmitter getEmitter(Long clientId) {
        return clients.get(clientId);
    }
}
