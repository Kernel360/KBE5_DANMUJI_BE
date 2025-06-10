package com.back2basics.company.port.in;

import com.back2basics.company.service.result.ReadCompanyResult;
import com.back2basics.user.service.result.UserSummaryResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadCompanyUseCase {

    ReadCompanyResult getCompany(Long id);

    Page<ReadCompanyResult> getCompaniesByNameContaining(Pageable pageable, String keyword);

    List<ReadCompanyResult> getAllCompanies();

    Page<ReadCompanyResult> getAllCompanies(Pageable pageable);

    List<UserSummaryResult> getUsersByCompanyId(Long companyId);
}
