package com.back2basics.notify.service;

import com.back2basics.notify.model.Notification;
import com.back2basics.notify.port.in.SubscribeNotificationUseCase;
import com.back2basics.notify.port.out.NotificationQueryPort;
import com.back2basics.notify.service.result.NotificationResult;
import com.back2basics.notify.util.SseEmitterUtil;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class SubscribeNotificationService implements SubscribeNotificationUseCase {

    private final NotificationQueryPort notificationQueryPort;
    private final SseEmitterUtil sseEmitterUtil;

    @Override
    public SseEmitter subscribe(Long clientId) throws IOException {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30분 유지

        sseEmitterUtil.add(clientId, emitter);

        emitter.onCompletion(() -> sseEmitterUtil.remove(clientId));
        emitter.onTimeout(() -> sseEmitterUtil.remove(clientId));
        emitter.onError(e -> sseEmitterUtil.remove(clientId));

        emitter.send(SseEmitter.event()
            .name("CONNECTED")
            .data("SSE stream connected"));

        // 읽지 않은 알림 전송
        List<Notification> unread = notificationQueryPort.findByClientIdAndIsReadFalse(clientId);
        if (unread.isEmpty()) {
            return emitter;
        }

        for (Notification notification : unread) {
            try {
                emitter.send(SseEmitter.event()
                    .name("ALERT")
                    .data(NotificationResult.from(notification)));
            } catch (IOException e) {
                emitter.completeWithError(e);
                sseEmitterUtil.remove(clientId);
                break;
            }
        }

        return emitter;
    }

}
