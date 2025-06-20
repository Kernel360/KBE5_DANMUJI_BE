package com.back2basics.company.port.out;

import com.back2basics.company.model.Company;

public interface CreateCompanyPort {

    Long save(Company company);

    boolean existsByName(String name);

    boolean existsByBizNo(Long bizNo);

    boolean existsByAddress(String addr);

}
