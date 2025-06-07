package com.back2basics.infra.exception.answer;

import com.back2basics.global.response.error.CustomException;

public class AnswerException extends CustomException {

    public AnswerException(AnswerErrorCode errorCode) {
        super(errorCode);
    }
}
