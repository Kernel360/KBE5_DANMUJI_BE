package com.back2basics.notify.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Component
public class SseEmitterUtil {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void add(Long clientId, SseEmitter emitter) {
        emitters.put(clientId, emitter);
    }

    public SseEmitter get(Long clientId) {
        return emitters.get(clientId);
    }

    public void remove(Long clientId) {
        emitters.remove(clientId);
    }

    public void send(Long clientId, Object data) {
        SseEmitter emitter = emitters.get(clientId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                    .name("ALERT")
                    .data(data));
                log.debug("알림 전송: clientId={}, data={}", clientId, data);
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(clientId);
                log.warn("알림 전송 실패 - Emitter 제거: clientId={}", clientId);
            }
        } else {
            log.debug("연결된 Emitter 없음: clientId={}", clientId);
        }
    }
}
