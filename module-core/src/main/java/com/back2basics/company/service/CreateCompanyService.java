package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.CreateCompanyUseCase;
import com.back2basics.company.port.in.command.CreateCompanyCommand;
import com.back2basics.company.port.out.CreateCompanyPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.validation.validator.CompanyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyService implements CreateCompanyUseCase {

    private final CreateCompanyPort createCompanyPort;
    private final CompanyValidator companyValidator;
    private final HistoryLogService historyLogService;

    @Override
    public Long createCompany(CreateCompanyCommand createCompanyCommand, Long loggedInUserId) {

        companyValidator.validateDuplicate(createCompanyCommand);

        Company company = Company.builder()
            .name(createCompanyCommand.getName())
            .ceoName(createCompanyCommand.getCeoName())
            .bio(createCompanyCommand.getBio())
            .bizNo(createCompanyCommand.getBizNo())
            .address(createCompanyCommand.getAddress())
            .email(createCompanyCommand.getEmail())
            .tel(createCompanyCommand.getTel())
            .build();

        Company savedCompany = createCompanyPort.save(company);
        historyLogService.logCreated(DomainType.COMPANY, loggedInUserId, savedCompany, "회사 신규 등록");

        return savedCompany.getId();
    }
}
