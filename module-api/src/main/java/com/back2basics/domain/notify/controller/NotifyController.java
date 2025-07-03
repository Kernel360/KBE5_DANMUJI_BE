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
import com.back2basics.security.jwt.JwtTokenProvider;
import com.back2basics.security.model.CustomUserDetails;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotifyController {

    private final JwtTokenProvider jwtTokenProvider;
    private final NotifyUseCase notifyUseCase;
    private final NotificationQueryUseCase notificationQueryUseCase;
    private final UpdateNotificationUseCase updateNotificationUseCase;
    private final SubscribeNotificationUseCase subscribeNotificationUseCase;

    // SSE 연결 및 읽지 않은 알림 전송
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
        @CookieValue(value = "accessToken", required = false) String accessToken)
        throws IOException {
        return subscribeNotificationUseCase.subscribe(jwtTokenProvider.getId(accessToken));
    }

    // 알림 생성 및 전송
    @PostMapping("/notify")
    public ResponseEntity<ApiResponse<Void>> notifyClient(
        @RequestBody NotificationCreateRequest request) {
        notifyUseCase.notify(request.toCommand());

        return ApiResponse.success(NOTIFICATION_CREATE_SUCCESS);
    }

    // 알림 읽음 처리
    @PutMapping("/read/{notificationId}")
    public ResponseEntity<ApiResponse<Void>> toggleRead(@PathVariable Long notificationId) {
        updateNotificationUseCase.toggleRead(notificationId);
        return ApiResponse.success(NOTIFICATION_UPDATE_READ_SUCCESS);
    }

    // 알림 전체 읽음 처리
    @PutMapping("/read/all")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        updateNotificationUseCase.markAllAsRead(userDetails.getId());
        return ApiResponse.success(NOTIFICATION_UPDATE_READ_ALL_SUCCESS);
    }

    // 전체 알림 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<NotificationResponse>>> getAll(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
        Page<NotificationResult> notifications = notificationQueryUseCase.findByClientId(
            userDetails.getId(), pageable);
        Page<NotificationResponse> responses = notifications.map(NotificationResponse::from);

        return ApiResponse.success(NOTIFICATION_READ_ALL_SUCCESS, responses);
    }

    // 읽지 않은 알림 개수 조회
    @GetMapping("/count/unread")
    public ResponseEntity<ApiResponse<Long>> countUnread(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        long count = notificationQueryUseCase.countUnreadByClientId(userDetails.getId());
        return ApiResponse.success(NOTIFICATION_READ_UNREAD_SUCCESS, count);
    }

    // 알림 삭제
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long notificationId) {
        updateNotificationUseCase.deleteById(notificationId);
        return ApiResponse.success(NOTIFICATION_DELETE_SUCCESS);
    }
}
