package com.back2basics.company.port.in;

import com.back2basics.company.service.result.CompanyInfoResult;

public interface GetCompanyByIdUseCase {

    CompanyInfoResult getCompany(Long id);

}
