package com.back2basics.company.port.in;

import com.back2basics.company.port.in.command.CompanyUpdateCommand;

public interface UpdateCompanyUseCase {

    void updateCompany(Long id, CompanyUpdateCommand command);
}
