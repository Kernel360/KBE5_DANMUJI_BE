package com.back2basics.port.in.company;

import com.back2basics.service.company.dto.CompanyCreateCommand;

public interface CreateCompanyUseCase {

    Long createCompany(CompanyCreateCommand companyCreateCommand);
}
