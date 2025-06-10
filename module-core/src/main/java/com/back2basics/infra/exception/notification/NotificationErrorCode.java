package com.back2basics.infra.exception.notification;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorCode implements ErrorCode {
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "N001", "알림을 찾을 수 없습니다."),
    NOTIFICATION_ALREADY_READ(HttpStatus.ALREADY_REPORTED, "N002", "이미 읽은 알림입니다."),
    NOTIFICATION_SEND_FAILED(HttpStatus.BAD_REQUEST, "N003", "알림 전송에 실패했습니다."),
    NOTIFICATION_TYPE_NOT_SUPPORTED(HttpStatus.FORBIDDEN, "N004", "지원하지 않는 알림 유형입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
