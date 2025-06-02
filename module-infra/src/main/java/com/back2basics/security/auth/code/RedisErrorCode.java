package com.back2basics.security.auth.code;

import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RedisErrorCode implements ErrorCode {
    REDIS_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C009", "Redis connection error");

    private final HttpStatus status;
    private final String code;
    private final String message;


}
