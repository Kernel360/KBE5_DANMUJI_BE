package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.UpdateCompanyUseCase;
import com.back2basics.company.port.in.command.UpdateCompanyCommand;
import com.back2basics.company.port.out.UpdateCompanyPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.validation.validator.CompanyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCompanyService implements UpdateCompanyUseCase {

    private final UpdateCompanyPort updateCompanyPort;
    private final CompanyValidator companyValidator;
    private final HistoryLogService historyLogService;

    @Override
    public void updateCompany(Long id, UpdateCompanyCommand command, Long loggedInUserId) {
        Company company = companyValidator.findCompany(id);
        Company before = Company.copyOf(company);

        company.update(command);
        updateCompanyPort.update(company);

        historyLogService.logUpdated(DomainType.COMPANY, loggedInUserId, before, company,
            "회사 정보 수정");
    }

}
