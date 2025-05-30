package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.DeleteCompanyUseCase;
import com.back2basics.company.port.in.UpdateCompanyUseCase;
import com.back2basics.company.port.in.command.UpdateCompanyCommand;
import com.back2basics.company.port.out.CompanyRepositoryPort;
import com.back2basics.infra.validation.validator.CompanyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements DeleteCompanyUseCase,
    UpdateCompanyUseCase {

    private final CompanyRepositoryPort companyRepositoryPort;
    private final CompanyValidator companyValidator;

    @Override
    public void updateCompany(Long id, UpdateCompanyCommand command) {
        Company company = companyValidator.findCompany(id);

        company.update(command);
        companyRepositoryPort.update(company);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepositoryPort.deleteById(id);
    }
}
