package com.back2basics.company.port.in;

import com.back2basics.company.port.in.command.CreateCompanyCommand;

public interface CreateCompanyUseCase {

    Long createCompany(CreateCompanyCommand createCompanyCommand);
}
