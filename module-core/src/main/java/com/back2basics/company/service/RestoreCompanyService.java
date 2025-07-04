package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.RestoreCompanyUseCase;
import com.back2basics.company.port.out.RestoreCompanyPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.validator.CompanyValidator;
import com.back2basics.infra.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestoreCompanyService implements RestoreCompanyUseCase {

    private final CompanyValidator companyValidator;
    private final HistoryLogService historyLogService;
    private final UserValidator userValidator;
    private final RestoreCompanyPort restoreCompanyPort;

    @Override
    public void restoreCompany(Long requesterId, Long companyId) {

        userValidator.isAdmin(requesterId);
        Company company = companyValidator.findCompany(companyId);

        company.restore();
        restoreCompanyPort.restoreCompany(company);



        historyLogService.logRestored(DomainType.COMPANY, requesterId, company, "비활성화 회사 복구");
    }

}
