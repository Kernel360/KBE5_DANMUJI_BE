package com.back2basics.company.port.in;

import com.back2basics.company.service.result.CompanyNameResult;
import com.back2basics.company.service.result.ReadCompanyResult;
import com.back2basics.company.service.result.ReadRecentCompanyResult;
import com.back2basics.user.service.result.UserListResult;
import com.back2basics.user.service.result.UserSummaryResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadCompanyUseCase {

    ReadCompanyResult getCompany(Long id);

    Page<ReadCompanyResult> getCompaniesByNameContaining(Pageable pageable, String keyword);

    List<ReadCompanyResult> getAllCompanies();

    List<CompanyNameResult> getAllNames();

    Page<ReadCompanyResult> getAllCompanies(Pageable pageable);

    List<UserSummaryResult> getUsersByCompanyId(Long companyId);

    Page<UserSummaryResult> getUsersByCompanyId(Long companyId, Pageable pageable);

    Page<UserListResult> getUsersInfoByCompanyId(Long companyId, Pageable pageable);

    List<ReadRecentCompanyResult> getRecentCompanies();

    Long getCompanyCounts();
}
