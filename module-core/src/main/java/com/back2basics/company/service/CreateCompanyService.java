package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.CreateCompanyUseCase;
import com.back2basics.company.port.in.command.CreateCompanyCommand;
import com.back2basics.company.port.out.CreateCompanyPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryRequestFactory;
import com.back2basics.history.service.HistoryCreateService;
import com.back2basics.infra.validation.validator.CompanyValidator;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyService implements CreateCompanyUseCase {

    private final CreateCompanyPort createCompanyPort;
    private final CompanyValidator companyValidator;
    private final HistoryCreateService historyCreateService;
    private final UserQueryPort userQueryPort;

    @Override
    public Long createCompany(CreateCompanyCommand createCompanyCommand, Long loggedInUserId) {

        User user = userQueryPort.findById(loggedInUserId);
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

        historyCreateService.create(
            HistoryRequestFactory.created(DomainType.COMPANY, user, company, "회사 등록"));

        return createCompanyPort.save(company);
    }
}
