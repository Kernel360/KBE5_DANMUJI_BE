package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.CreateCompanyUseCase;
import com.back2basics.company.port.in.DeleteCompanyUseCase;
import com.back2basics.company.port.in.GetAllCompaniesUseCase;
import com.back2basics.company.port.in.GetCompanyByIdUseCase;
import com.back2basics.company.port.in.UpdateCompanyUseCase;
import com.back2basics.company.port.in.command.CompanyCreateCommand;
import com.back2basics.company.port.in.command.CompanyUpdateCommand;
import com.back2basics.company.port.out.CompanyRepositoryPort;
import com.back2basics.company.service.result.CompanyInfoResult;
import com.back2basics.infra.validation.validator.CompanyValidator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CreateCompanyUseCase, DeleteCompanyUseCase,
    GetCompanyByIdUseCase, GetAllCompaniesUseCase, UpdateCompanyUseCase {

    private final CompanyRepositoryPort companyRepositoryPort;
    private final CompanyValidator companyValidator;

    @Override
    public Long createCompany(CompanyCreateCommand companyCreateCommand) {
        Company company = Company.builder()
            .name(companyCreateCommand.getName())
            .ceoName(companyCreateCommand.getCeoName())
            .bio(companyCreateCommand.getBio())
            .companyType(companyCreateCommand.getCompanyType())
            .bizNo(companyCreateCommand.getBizNo())
            .address(companyCreateCommand.getAddress())
            .email(companyCreateCommand.getEmail())
            .tel(companyCreateCommand.getTel())
            .build();
        return companyRepositoryPort.save(company);
    }

    @Override
    public CompanyInfoResult getCompany(Long id) {
        Company company = companyValidator.findCompany(id);
        return CompanyInfoResult.from(company);
    }

    @Override
    public List<CompanyInfoResult> getAllCompanies() {
        return companyRepositoryPort.findAll().stream()
            .map(CompanyInfoResult::from)
            .collect(Collectors.toList());
    }

    @Override
    public void updateCompany(Long id, CompanyUpdateCommand command) {
        Company company = companyValidator.findCompany(id);

        company.update(command);
        companyRepositoryPort.update(company);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepositoryPort.deleteById(id);
    }
}
