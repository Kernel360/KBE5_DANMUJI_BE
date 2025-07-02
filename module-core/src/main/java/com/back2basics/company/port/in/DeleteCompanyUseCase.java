package com.back2basics.company.port.in;

import com.back2basics.company.model.Company;

public interface DeleteCompanyUseCase {

    Company deleteCompany(Long id, Long loggedInUserId);
}
