package com.back2basics.notify.port.out;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseEmitterRepository {

    private final Map<Long, SseEmitter> clients = new ConcurrentHashMap<>();

    public void add(Long clientId, SseEmitter emitter) {
        clients.put(clientId, emitter);
    }

    public SseEmitter get(Long clientId) {
        return clients.get(clientId);
    }

    public void remove(Long clientId) {
        clients.remove(clientId);
    }
}
