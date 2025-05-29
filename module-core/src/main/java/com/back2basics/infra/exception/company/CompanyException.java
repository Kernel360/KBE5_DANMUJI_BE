package com.back2basics.infra.exception.company;

import com.back2basics.global.response.error.CustomException;
import com.back2basics.global.response.error.ErrorResponse.FieldError;
import java.util.List;

public class CompanyException extends CustomException {

    public CompanyException(CompanyErrorCode errorCode) {
        super(errorCode);
    }

    public CompanyException(CompanyErrorCode errorCode, List<FieldError> errors) {
        super(errorCode, errors);
    }
}
