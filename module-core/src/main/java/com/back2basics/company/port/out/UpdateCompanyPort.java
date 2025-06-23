package com.back2basics.company.port.out;

import com.back2basics.company.model.Company;

public interface UpdateCompanyPort {

    void update(Company company);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByBizNoAndIdNot(Long bizNo, Long id);

    boolean existsByAddressAndIdNot(String address, Long id);
}
