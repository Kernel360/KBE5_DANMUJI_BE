package com.back2basics.domain.user.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserResponseCode implements ResponseCode {

    USER_CREATE_SUCCESS(HttpStatus.CREATED, "U201", "사용자 생성 완료"),
    USER_READ_SUCCESS(HttpStatus.OK, "U202", "사용자 조회 완료"),
    USER_READ_ALL_SUCCESS(HttpStatus.OK, "U203", "사용자 목록 조회 완료"),
    USER_UPDATE_SUCCESS(HttpStatus.OK, "U204", "사용자 수정 완료"),
    USER_DELETE_SUCCESS(HttpStatus.OK, "U205", "사용자 삭제 완료"),
    USER_CHANGE_PASSWORD_SUCCESS(HttpStatus.OK, "U206", "사용자 비밀번호 변경 완료"),
    USER_RESET_PASSWORD_SUCCESS(HttpStatus.OK, "U206", "사용자 비밀번호 변경 완료"),
    USER_SEND_MAIL_SUCCESS(HttpStatus.OK, "U207", "사용자 비밀번호 재설정 이메일 발송 완료"),
    USER_EXISTS_SUCCESS(HttpStatus.OK, "U208", "사용자 존재 여부 확인 완료"),
    USER_UPDATE_ROLE_SUCCESS(HttpStatus.OK, "U209", "사용자 역할 업데이트 완료"),
    USER_POSITIONS_READ_SUCCESS(HttpStatus.OK, "U210", "사용자 직책 조회 완료"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
