package com.back2basics.company.port.in;

import com.back2basics.service.company.dto.CompanyUpdateCommand;

public interface UpdateCompanyUseCase {

    void updateCompany(Long id, CompanyUpdateCommand command);
}
