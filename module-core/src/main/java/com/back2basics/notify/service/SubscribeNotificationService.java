package com.back2basics.notify.service;

import com.back2basics.notify.model.Notification;
import com.back2basics.notify.port.in.SubscribeNotificationUseCase;
import com.back2basics.notify.port.out.NotificationQueryPort;
import com.back2basics.notify.port.out.SseEmitterRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class SubscribeNotificationService implements SubscribeNotificationUseCase {

    private final NotificationQueryPort notificationQueryPort;
    private final SseEmitterRepository sseEmitterRepository;

    @Override
    public SseEmitter subscribe(Long clientId) {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30분 유지
        sseEmitterRepository.add(clientId, emitter);

        emitter.onCompletion(() -> sseEmitterRepository.remove(clientId));
        emitter.onTimeout(() -> sseEmitterRepository.remove(clientId));
        emitter.onError(e -> sseEmitterRepository.remove(clientId));

        // 읽지 않은 알림 전송
        List<Notification> unread = notificationQueryPort.findByClientIdAndIsReadFalse(clientId);
        unread.forEach(notification -> {
            try {
                emitter.send(SseEmitter.event()
                    .name("ALERT")
                    .data(notification));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

}
