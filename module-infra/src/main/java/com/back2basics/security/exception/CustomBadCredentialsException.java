package com.back2basics.security.exception;

import com.back2basics.global.response.code.AuthErrorCode;
import com.back2basics.global.response.error.CustomException;

public class CustomBadCredentialsException extends CustomException {

    public CustomBadCredentialsException(AuthErrorCode errorCode) {
        super(errorCode);
    }
}
