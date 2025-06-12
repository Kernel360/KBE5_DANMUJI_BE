package com.back2basics.infra.exception.notification;

import com.back2basics.global.response.error.CustomException;

public class NotificationException extends CustomException {

    public NotificationException(NotificationErrorCode errorCode) {
        super(errorCode);
    }
}
