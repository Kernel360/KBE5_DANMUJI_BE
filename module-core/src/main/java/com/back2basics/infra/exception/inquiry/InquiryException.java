package com.back2basics.infra.exception.inquiry;

import com.back2basics.global.response.error.CustomException;
import com.back2basics.global.response.error.ErrorResponse.FieldError;
import java.util.List;

public class InquiryException extends CustomException {

    public InquiryException(InquiryErrorCode errorCode) {
        super(errorCode);
    }

    public InquiryException(InquiryErrorCode errorCode, List<FieldError> errors) {
        super(errorCode, errors);
    }

}
