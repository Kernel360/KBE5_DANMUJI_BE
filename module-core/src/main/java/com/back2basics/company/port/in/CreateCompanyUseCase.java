package com.back2basics.company.port.in;

import com.back2basics.company.port.in.command.CompanyCreateCommand;

public interface CreateCompanyUseCase {

    Long createCompany(CompanyCreateCommand companyCreateCommand);
}
