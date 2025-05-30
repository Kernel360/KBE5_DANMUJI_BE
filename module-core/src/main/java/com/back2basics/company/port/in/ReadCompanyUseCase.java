package com.back2basics.company.port.in;

import com.back2basics.company.service.result.ReadCompanyResult;
import java.util.List;

public interface ReadCompanyUseCase {

    ReadCompanyResult getCompany(Long id);

    List<ReadCompanyResult> getAllCompanies();

}
