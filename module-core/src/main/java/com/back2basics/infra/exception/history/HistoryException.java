package com.back2basics.infra.exception.history;

import com.back2basics.global.response.error.CustomException;

public class HistoryException extends CustomException {

    public HistoryException(HistoryErrorCode errorCode) {
        super(errorCode);
    }

}
