package com.back2basics.security.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthResponseCode implements ResponseCode {

    SUCCESS_LOGOUT(HttpStatus.OK, "A001", "로그아웃되었습니다."),
    SUCCESS_LOGIN(HttpStatus.OK, "A002", "로그인되었습니다."),
    SUCCESS_REISSUE(HttpStatus.OK, "A003", "재발급 되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
