package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.UpdateCompanyUseCase;
import com.back2basics.company.port.in.command.UpdateCompanyCommand;
import com.back2basics.company.port.out.UpdateCompanyPort;
import com.back2basics.infra.validation.validator.CompanyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCompanyService implements UpdateCompanyUseCase {

    private final UpdateCompanyPort updateCompanyPort;
    private final CompanyValidator companyValidator;

    @Override
    public void updateCompany(Long id, UpdateCompanyCommand command) {
        companyValidator.validateDuplicate(command, id);

        Company company = companyValidator.findCompany(id);

        company.update(command);
        updateCompanyPort.update(company);
    }

}
