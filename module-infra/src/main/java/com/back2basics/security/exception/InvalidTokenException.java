package com.back2basics.security.exception;

import com.back2basics.global.response.code.ErrorCode;
import com.back2basics.global.response.error.CustomException;

public class InvalidTokenException extends CustomException {

    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
