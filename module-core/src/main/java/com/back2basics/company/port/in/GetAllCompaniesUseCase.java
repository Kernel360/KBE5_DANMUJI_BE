package com.back2basics.company.port.in;

import com.back2basics.company.service.result.CompanyInfoResult;
import java.util.List;

public interface GetAllCompaniesUseCase {

    List<CompanyInfoResult> getAllCompanies();

}
