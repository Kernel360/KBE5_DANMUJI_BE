package com.back2basics.domain.notify.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotificationResponseCode implements ResponseCode {

    NOTIFICATION_CREATE_SUCCESS(HttpStatus.CREATED, "N201", "알림 생성 완료"),
    NOTIFICATION_READ_SUCCESS(HttpStatus.OK, "N202", "알림 조회 완료"),
    NOTIFICATION_READ_ALL_SUCCESS(HttpStatus.OK, "N203", "알림 목록 조회 완료"),
    NOTIFICATION_UPDATE_READ_SUCCESS(HttpStatus.OK, "N204", "알림 읽음 처리 완료"),
    NOTIFICATION_UPDATE_READ_ALL_SUCCESS(HttpStatus.OK, "N204", "알림 전체 읽음 처리 완료"),
    NOTIFICATION_DELETE_SUCCESS(HttpStatus.OK, "N205", "알림 삭제 완료"),
    NOTIFICATION_READ_UNREAD_SUCCESS(HttpStatus.OK, "N206", "읽지 않은 알림 개수 조회 완료"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
