package com.back2basics.service.company.exception;

import com.back2basics.model.company.Company;
import com.back2basics.port.in.company.CreateCompanyUseCase;
import com.back2basics.port.in.company.DeleteCompanyUseCase;
import com.back2basics.port.out.company.CompanyRepositoryPort;
import com.back2basics.service.company.dto.CompanyCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CreateCompanyUseCase, DeleteCompanyUseCase {

    private final CompanyRepositoryPort companyRepositoryPort;

    @Override
    public Long createCompany(CompanyCreateCommand companyCreateCommand) {
        Company company = Company.builder()
            .name(companyCreateCommand.getName())
            .ceoName(companyCreateCommand.getCeoName())
            .bio(companyCreateCommand.getBio())
            .companyType(companyCreateCommand.getCompanyType())
            .bizNo(companyCreateCommand.getBizNo())
            .address(companyCreateCommand.getAddress())
            .build();
        return companyRepositoryPort.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepositoryPort.deleteById(id);
    }
}
