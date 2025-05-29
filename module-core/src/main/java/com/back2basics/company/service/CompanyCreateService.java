package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.CreateCompanyUseCase;
import com.back2basics.company.port.in.command.CompanyCreateCommand;
import com.back2basics.company.port.out.CompanyCreatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyCreateService implements CreateCompanyUseCase {

    private final CompanyCreatePort companyCreatePort;

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
        return companyCreatePort.save(company);
    }
}
