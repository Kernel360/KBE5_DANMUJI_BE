package com.back2basics.notify.util;

import com.back2basics.notify.model.Notification;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import com.back2basics.notify.port.out.NotificationSavePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSubscriber {

    private final ObjectMapper objectMapper;
    private final NotificationSavePort notificationSavePort;
    private final SseEmitterUtil sseEmitterUtil;

    public void handleMessage(Object message) {
        try {
            SendNotificationCommand command = objectMapper.readValue(
                message.toString(), // JSON 문자열로 처리
                SendNotificationCommand.class
            );
            Notification saved = notificationSavePort.save(command.toEntity()); // DB 저장
            sseEmitterUtil.send(command.clientId(), saved);         // SSE 전송
        } catch (Exception e) {
            log.error("알림 수신 실패", e);
        }
    }
}