package com.back2basics.security.exception;

import com.back2basics.global.response.error.CustomException;
import com.back2basics.security.auth.code.AuthErrorCode;

public class CustomBadCredentialsException extends CustomException {

    public CustomBadCredentialsException(AuthErrorCode errorCode) {
        super(errorCode);
    }
}
