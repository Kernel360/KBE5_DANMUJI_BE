package com.back2basics.security.exception;

import com.back2basics.global.response.code.ErrorCode;
import com.back2basics.global.response.error.CustomException;

public class CustomRedisConnectionException extends CustomException {

    public CustomRedisConnectionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
