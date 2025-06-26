package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.DeleteCompanyUseCase;
import com.back2basics.company.port.out.DeleteCompanyPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.validation.validator.CompanyValidator;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserCommandPort;
import com.back2basics.user.port.out.UserQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCompanyService implements DeleteCompanyUseCase {

    private final DeleteCompanyPort deleteCompanyPort;
    private final CompanyValidator companyValidator;
    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;
    private final HistoryLogService historyLogService;

    @Override
    public void deleteCompany(Long id, Long loggedInUserId) {
        Company company = companyValidator.findCompany(id);

        List<User> users = userQueryPort.findAllByCompanyIdAndDeletedAtIsNull(id);
        userCommandPort.softDeleteByCompanyId(id);

        company.markDeleted();
        Company deletedCompany = deleteCompanyPort.softDelete(company);

        historyLogService.logDeleted(DomainType.COMPANY, loggedInUserId, deletedCompany, "업체 비활성화");
    }

}
