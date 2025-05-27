package com.back2basics.port.out.company;

import com.back2basics.model.company.Company;
import java.util.List;
import java.util.Optional;

public interface CompanyRepositoryPort {

    Long save(Company company);

    Optional<Company> findById(Long id);

    List<Company> findAll();

    void update(Company company);

    void deleteById(Long id);
}
