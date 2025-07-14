package com.back2basics.infra.exception.global;

import com.back2basics.global.response.error.CustomException;

public class ForbiddenAccessException extends CustomException {

    public ForbiddenAccessException(String message) {
        super(message, GlobalErrorCode.FORBIDDEN_ACCESS);
    }

}
