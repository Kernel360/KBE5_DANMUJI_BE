package com.back2basics.service.company;

import com.back2basics.infra.validation.CompanyValidator;
import com.back2basics.model.company.Company;
import com.back2basics.port.in.company.CreateCompanyUseCase;
import com.back2basics.port.in.company.DeleteCompanyUseCase;
import com.back2basics.port.in.company.GetAllCompaniesUseCase;
import com.back2basics.port.in.company.GetCompanyByIdUseCase;
import com.back2basics.port.in.company.UpdateCompanyUseCase;
import com.back2basics.port.out.company.CompanyRepositoryPort;
import com.back2basics.service.company.dto.CompanyCreateCommand;
import com.back2basics.service.company.dto.CompanyResponseDto;
import com.back2basics.service.company.dto.CompanyUpdateCommand;
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
            .build();
        return companyRepositoryPort.save(company);
    }

    @Override
    public CompanyResponseDto getCompany(Long id) {
        Company company = companyValidator.findCompany(id);
        return CompanyResponseDto.from(company);
    }

    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        return companyRepositoryPort.findAll().stream()
            .map(CompanyResponseDto::from)
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
