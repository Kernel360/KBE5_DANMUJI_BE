package com.back2basics.notify.port.in;

import java.io.IOException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SubscribeNotificationUseCase {

    SseEmitter subscribe(Long clientId) throws IOException;
}
