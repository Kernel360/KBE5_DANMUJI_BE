package com.back2basics.domain.notify.controller;

import static com.back2basics.domain.notify.controller.code.NotificationResponseCode.NOTIFICATION_CREATE_SUCCESS;
import static com.back2basics.domain.notify.controller.code.NotificationResponseCode.NOTIFICATION_DELETE_SUCCESS;
import static com.back2basics.domain.notify.controller.code.NotificationResponseCode.NOTIFICATION_READ_ALL_SUCCESS;
import static com.back2basics.domain.notify.controller.code.NotificationResponseCode.NOTIFICATION_READ_UNREAD_SUCCESS;
import static com.back2basics.domain.notify.controller.code.NotificationResponseCode.NOTIFICATION_UPDATE_READ_ALL_SUCCESS;
import static com.back2basics.domain.notify.controller.code.NotificationResponseCode.NOTIFICATION_UPDATE_READ_SUCCESS;

import com.back2basics.domain.notify.dto.request.NotificationCreateRequest;
import com.back2basics.domain.notify.dto.response.NotificationResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.notify.port.in.NotificationQueryUseCase;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.SubscribeNotificationUseCase;
import com.back2basics.notify.port.in.UpdateNotificationUseCase;
import com.back2basics.notify.service.result.NotificationResult;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotifyController {

    private final NotifyUseCase notifyUseCase;
    private final NotificationQueryUseCase notificationQueryUseCase;
    private final UpdateNotificationUseCase updateNotificationUseCase;
    private final SubscribeNotificationUseCase subscribeNotificationUseCase;

    // SSE 연결 및 읽지 않은 알림 전송
    @GetMapping("/subscribe/{clientId}")
    public SseEmitter subscribe(@PathVariable Long clientId) throws IOException {
        return subscribeNotificationUseCase.subscribe(clientId);
    }

    // 알림 생성 및 전송
    @PostMapping("/notify/{clientId}")
    public ResponseEntity<?> notifyClient(@PathVariable Long clientId,
        @RequestBody NotificationCreateRequest request) {
        notifyUseCase.notify(clientId, request.message(), request.type());

        return ApiResponse.success(NOTIFICATION_CREATE_SUCCESS);
    }

    // 알림 읽음 처리
    @PostMapping("/read/{notificationId}")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        updateNotificationUseCase.markAsRead(notificationId);
        return ApiResponse.success(NOTIFICATION_UPDATE_READ_SUCCESS);
    }

    // 알림 전체 읽음 처리
    @PostMapping("/read/all/{clientId}")
    public ResponseEntity<?> markAllAsRead(@PathVariable Long clientId) {
        updateNotificationUseCase.markAllAsRead(clientId);
        return ApiResponse.success(NOTIFICATION_UPDATE_READ_ALL_SUCCESS);
    }

    // 전체 알림 조회
    @GetMapping("/list/{clientId}")
    public ResponseEntity<?> listAll(@PathVariable Long clientId) {
        List<NotificationResult> notifications = notificationQueryUseCase.findByClientId(clientId);
        List<NotificationResponse> responses = notifications.stream()
            .map(NotificationResponse::from)
            .toList();

        return ApiResponse.success(NOTIFICATION_READ_ALL_SUCCESS, responses);
    }

    // 읽지 않은 알림 개수 조회
    @GetMapping("/count/unread/{clientId}")
    public ResponseEntity<?> countUnread(@PathVariable Long clientId) {
        long count = notificationQueryUseCase.countUnreadByClientId(clientId);
        return ApiResponse.success(NOTIFICATION_READ_UNREAD_SUCCESS, count);
    }

    // 알림 삭제
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteById(@PathVariable Long notificationId) {
        updateNotificationUseCase.deleteById(notificationId);
        return ApiResponse.success(NOTIFICATION_DELETE_SUCCESS);
    }
}
