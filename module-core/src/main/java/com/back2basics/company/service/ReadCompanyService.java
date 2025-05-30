package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.ReadCompanyUseCase;
import com.back2basics.company.port.out.ReadCompanyPort;
import com.back2basics.company.service.result.ReadCompanyResult;
import com.back2basics.infra.validation.validator.CompanyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadCompanyService implements ReadCompanyUseCase {

    private final ReadCompanyPort readCompanyPort;
    private final CompanyValidator companyValidator;


    @Override
    public ReadCompanyResult getCompany(Long id) {
        Company company = companyValidator.findCompany(id);
        return ReadCompanyResult.toResult(company);
    }

    @Override
    public Page<ReadCompanyResult> getAllCompanies(Pageable pageable) {
        return readCompanyPort.findAll(pageable)
            .map(ReadCompanyResult::toResult);
    }

}
