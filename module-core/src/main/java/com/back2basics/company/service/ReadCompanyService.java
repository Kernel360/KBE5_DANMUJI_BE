package com.back2basics.company.service;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.in.ReadCompanyUseCase;
import com.back2basics.company.port.out.ReadCompanyPort;
import com.back2basics.company.service.result.ReadCompanyResult;
import com.back2basics.company.service.result.ReadRecentCompanyResult;
import com.back2basics.infra.validator.CompanyValidator;
import com.back2basics.user.port.out.UserQueryPort;
import com.back2basics.user.service.result.UserListResult;
import com.back2basics.user.service.result.UserSummaryResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadCompanyService implements ReadCompanyUseCase {

    private final ReadCompanyPort readCompanyPort;
    private final UserQueryPort userQueryPort;
    private final CompanyValidator companyValidator;

    @Override
    public ReadCompanyResult getCompany(Long id) {
        Company company = companyValidator.findCompany(id);
        return ReadCompanyResult.toResult(company);
    }

    @Override
    public Page<ReadCompanyResult> getCompaniesByNameContaining(Pageable pageable, String keyword) {
        return readCompanyPort.findByNameContaining(pageable, keyword)
            .map(ReadCompanyResult::toResult);

    }

    @Override
    public Page<ReadCompanyResult> getAllCompanies(Pageable pageable) {
        return readCompanyPort.findAll(pageable)
            .map(ReadCompanyResult::toResult);
    }

    @Override
    public List<ReadCompanyResult> getAllCompanies() {
        return readCompanyPort.getAllCompanies()
            .stream().map(ReadCompanyResult::toResult).toList();
    }

    @Override
    public List<UserSummaryResult> getUsersByCompanyId(Long companyId) {
        companyValidator.validateCompanyExists(companyId);
        return userQueryPort.findAllByCompanyId(companyId).stream()
            .map(UserSummaryResult::from).toList();
    }

    @Override
    public List<UserListResult> getUsersInfoByCompanyId(Long companyId) {
        companyValidator.validateCompanyExists(companyId);
        return userQueryPort.findAllByCompanyId(companyId).stream()
            .map(UserListResult::from).toList();
    }

    @Override
    public List<ReadRecentCompanyResult> getRecentCompanies() {
        return readCompanyPort.getRecentCompanies()
            .stream().map(ReadRecentCompanyResult::toResult).toList();
    }

}
