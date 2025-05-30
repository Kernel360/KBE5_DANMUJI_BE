package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.ReadCompanyUseCase;
import com.back2basics.company.port.out.ReadCompanyPort;
import com.back2basics.company.service.result.ReadCompanyResult;
import com.back2basics.infra.validation.validator.CompanyValidator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
    public List<ReadCompanyResult> getAllCompanies() {
        return readCompanyPort.findAll().stream()
            .map(ReadCompanyResult::toResult)
            .collect(Collectors.toList());
    }

}
