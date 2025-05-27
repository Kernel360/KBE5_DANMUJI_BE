package com.back2basics.infra.company.validation;

import com.back2basics.model.company.Company;
import com.back2basics.port.out.company.CompanyRepositoryPort;
import com.back2basics.service.company.exception.CompanyErrorCode;
import com.back2basics.service.company.exception.CompanyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyValidator {

    private final CompanyRepositoryPort companyRepositoryPort;
 
    public Company findCompany(Long id) {
        return companyRepositoryPort.findById(id)
            .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));
    }

}
