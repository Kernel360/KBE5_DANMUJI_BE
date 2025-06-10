package com.back2basics.infra.exception.company;

import com.back2basics.global.response.error.ErrorResponse;
import java.util.List;

public class DuplicateCompanyNameException extends CompanyException {

    public DuplicateCompanyNameException(String name) {
        super(CompanyErrorCode.DUPLICATE_COMPANY_NAME);
    }

    public DuplicateCompanyNameException(String name, List<ErrorResponse.FieldError> errors) {
        super(CompanyErrorCode.DUPLICATE_COMPANY_NAME, errors);
    }

}
