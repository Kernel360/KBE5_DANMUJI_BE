package com.back2basics.company.port.out;

import com.back2basics.company.model.Company;

public interface DeleteCompanyPort {

    Company softDelete(Company company);

}
