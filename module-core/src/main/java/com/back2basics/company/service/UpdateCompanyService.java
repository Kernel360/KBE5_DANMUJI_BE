package com.back2basics.company.service;

import static com.back2basics.infra.exception.company.CompanyErrorCode.COMPANY_NOT_FOUND;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.UpdateCompanyUseCase;
import com.back2basics.company.port.in.command.UpdateCompanyCommand;
import com.back2basics.company.port.out.ReadCompanyPort;
import com.back2basics.company.port.out.UpdateCompanyPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryRequestFactory;
import com.back2basics.history.service.HistoryCreateService;
import com.back2basics.infra.exception.company.CompanyException;
import com.back2basics.infra.validation.validator.CompanyValidator;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCompanyService implements UpdateCompanyUseCase {

    private final UpdateCompanyPort updateCompanyPort;
    private final CompanyValidator companyValidator;
    private final HistoryCreateService historyCreateService;
    private final UserQueryPort userQueryPort;
    private final ReadCompanyPort readCompanyPort;

    @Override
    public void updateCompany(Long id, UpdateCompanyCommand command, Long loggedInUserId) {
        Company company = companyValidator.findCompany(id);
        Company before = Company.copyOf(company);

        company.update(command);
        updateCompanyPort.update(company);

        User user = userQueryPort.findById(loggedInUserId);
        Company deletedCompany = readCompanyPort.findById(id)
            .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));
        historyCreateService.create(
            HistoryRequestFactory.updated(DomainType.COMPANY, user, before, deletedCompany,
                "회사 정보 수정"));
    }

}
