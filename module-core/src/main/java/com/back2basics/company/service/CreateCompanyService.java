package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.CreateCompanyUseCase;
import com.back2basics.company.port.in.command.CreateCompanyCommand;
import com.back2basics.company.port.out.CreateCompanyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyService implements CreateCompanyUseCase {

    private final CreateCompanyPort createCompanyPort;

    @Override
    public Long createCompany(CreateCompanyCommand createCompanyCommand) {
        Company company = Company.builder()
            .name(createCompanyCommand.getName())
            .ceoName(createCompanyCommand.getCeoName())
            .bio(createCompanyCommand.getBio())
            .companyType(createCompanyCommand.getCompanyType())
            .bizNo(createCompanyCommand.getBizNo())
            .address(createCompanyCommand.getAddress())
            .email(createCompanyCommand.getEmail())
            .tel(createCompanyCommand.getTel())
            .build();
        return createCompanyPort.save(company);
    }
}
