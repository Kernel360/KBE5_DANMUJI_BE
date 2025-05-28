package com.back2basics.company.port.in;

import com.back2basics.service.company.dto.CompanyCreateCommand;

public interface CreateCompanyUseCase {

    Long createCompany(CompanyCreateCommand companyCreateCommand);
}
