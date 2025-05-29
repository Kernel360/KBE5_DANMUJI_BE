package com.back2basics.company.port.in;

import com.back2basics.company.port.in.command.UpdateCompanyCommand;

public interface UpdateCompanyUseCase {

    void updateCompany(Long id, UpdateCompanyCommand command);
}
