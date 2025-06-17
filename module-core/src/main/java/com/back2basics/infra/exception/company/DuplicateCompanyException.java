package com.back2basics.infra.exception.company;

import com.back2basics.global.response.error.ErrorResponse.FieldError;
import java.util.List;

public class DuplicateCompanyException extends CompanyException {

    public DuplicateCompanyException(List<FieldError> errors) {
        super(CompanyErrorCode.DUPLICATE_COMPANY_INFO, errors);
    }
}
