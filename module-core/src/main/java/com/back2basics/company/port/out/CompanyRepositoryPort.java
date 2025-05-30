package com.back2basics.company.port.out;

import com.back2basics.company.model.Company;

public interface CompanyRepositoryPort {

    void update(Company company);

    void deleteById(Long id);
}
