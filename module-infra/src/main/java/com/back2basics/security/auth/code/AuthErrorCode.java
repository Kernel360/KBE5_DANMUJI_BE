package com.back2basics.security.auth.code;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    AUTH_REQUIRED(HttpStatus.UNAUTHORIZED, "A001", "인증이 필요합니다."),
    TOKEN_REQUIRED(HttpStatus.UNAUTHORIZED, "A002", "JWT 토큰이 필요합니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A003", "JWT 토큰이 만료되었습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "A004", "유효하지 않은 JWT 토큰입니다."),
    TOKEN_DECRYPTION_FAILED(HttpStatus.UNAUTHORIZED, "A005", "JWT 토큰 복호화에 실패하였습니다."),
    TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "A006", "지원되지 않는 JWT 토큰입니다."),
    TOKEN_MALFORMED(HttpStatus.UNAUTHORIZED, "A007", "잘못된 JWT 토큰 형식입니다."),
    TOKEN_SIGNATURE_INVALID(HttpStatus.UNAUTHORIZED, "A008", "유효하지 않은 JWT 서명입니다."),
    TOKEN_BLACKLISTED(HttpStatus.UNAUTHORIZED, "A009", "차단된 JWT 토큰입니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A010", "존재하지 않는 JWT 토큰입니다."),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, "A011", "접근 권한이 없습니다."),

    ALREADY_LOGOUT(HttpStatus.BAD_REQUEST, "A012", "이미 로그아웃된 사용자입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
