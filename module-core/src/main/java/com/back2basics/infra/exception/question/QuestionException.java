package com.back2basics.infra.exception.question;

import com.back2basics.global.response.error.CustomException;


public class QuestionException extends CustomException {

    public QuestionException(QuestionErrorCode errorCode) {
        super(errorCode);
    }
}
