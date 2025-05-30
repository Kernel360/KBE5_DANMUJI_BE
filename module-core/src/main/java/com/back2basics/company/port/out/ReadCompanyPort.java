package com.back2basics.company.port.out;

import com.back2basics.company.model.Company;
import java.util.List;
import java.util.Optional;

public interface ReadCompanyPort {

    Optional<Company> findById(Long id);

    List<Company> findAll();

}
