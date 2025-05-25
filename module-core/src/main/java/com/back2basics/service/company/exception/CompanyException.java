package com.back2basics.service.company.exception;

import com.back2basics.response.global.error.CustomException;
import com.back2basics.response.global.error.ErrorResponse.FieldError;
import java.util.List;

public class CompanyException extends CustomException {

    public CompanyException(CompanyErrorCode errorCode) {
        super(errorCode);
    }

    public CompanyException(CompanyErrorCode errorCode, List<FieldError> errors) {
        super(errorCode, errors);
    }
}
