package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.CreateCompanyUseCase;
import com.back2basics.company.port.in.command.CreateCompanyCommand;
import com.back2basics.company.port.out.CreateCompanyPort;
import com.back2basics.infra.exception.company.DuplicateCompanyNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyService implements CreateCompanyUseCase {

    private final CreateCompanyPort createCompanyPort;

    @Override
    public Long createCompany(CreateCompanyCommand createCompanyCommand) {

        if (createCompanyPort.existsByName(createCompanyCommand.getName())) {
            throw new DuplicateCompanyNameException(createCompanyCommand.getName());
        }

        Company company = Company.builder()
            .name(createCompanyCommand.getName())
            .ceoName(createCompanyCommand.getCeoName())
            .bio(createCompanyCommand.getBio())
            .bizNo(createCompanyCommand.getBizNo())
            .address(createCompanyCommand.getAddress())
            .email(createCompanyCommand.getEmail())
            .tel(createCompanyCommand.getTel())
            .build();
        return createCompanyPort.save(company);
    }
}
