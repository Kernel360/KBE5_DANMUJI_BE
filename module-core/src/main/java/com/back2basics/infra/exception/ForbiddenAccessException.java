package com.back2basics.infra.exception;

import com.back2basics.global.response.error.CustomException;
import com.back2basics.infra.exception.inquiry.InquiryErrorCode;

public class ForbiddenAccessException extends CustomException {

    public ForbiddenAccessException(String message) {
        super(message, InquiryErrorCode.INQUIRY_DELETE_NOT_ALLOWED);
    }

}
