package com.back2basics.global.security.exception;

import com.back2basics.global.response.error.CustomException;
import com.back2basics.security.code.AuthErrorCode;

public class CustomBadCredentialsException extends CustomException {

    public CustomBadCredentialsException(AuthErrorCode errorCode) {
        super(errorCode);
    }
}
