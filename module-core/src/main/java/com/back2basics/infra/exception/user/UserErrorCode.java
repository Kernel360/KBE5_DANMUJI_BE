package com.back2basics.infra.exception.user;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다"),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "U002", "이미 존재하는 아이디입니다."),
    PASSWORD_MISMATCH(HttpStatus.FORBIDDEN, "U003", "현재 비밀번호가 일치하지 않습니다."),
    VERIFICATION_FAILED(HttpStatus.FORBIDDEN, "U004", "인증에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
