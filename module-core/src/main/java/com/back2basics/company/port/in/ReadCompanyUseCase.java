package com.back2basics.company.port.in;

import com.back2basics.company.service.result.ReadCompanyResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadCompanyUseCase {

    ReadCompanyResult getCompany(Long id);

    List<ReadCompanyResult> getCompaniesByNameContaining(String keyword);

    Page<ReadCompanyResult> getAllCompanies(Pageable pageable);

}
