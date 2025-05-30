package com.back2basics.infra.validation.validator;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.out.ReadCompanyPort;
import com.back2basics.infra.exception.company.CompanyErrorCode;
import com.back2basics.infra.exception.company.CompanyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyValidator {

    private final ReadCompanyPort readCompanyPort;

    public Company findCompany(Long id) {
        return readCompanyPort.findById(id)
            .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));
    }

}
