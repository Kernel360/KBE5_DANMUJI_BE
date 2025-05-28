package com.back2basics.infra.exception.user;

import com.back2basics.global.response.error.CustomException;

public class UserException extends CustomException {

    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
