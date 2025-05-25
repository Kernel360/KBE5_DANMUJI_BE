package com.back2basics.port.in.company;

import com.back2basics.service.company.dto.CompanyUpdateCommand;

public interface UpdateCompanyUseCase {

    void updateCompany(Long id, CompanyUpdateCommand command);
}
