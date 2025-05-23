package com.back2basics.port.out.company;

import com.back2basics.model.company.Company;

public interface CompanyRepositoryPort {
  Long save(Company company);

  public void deleteById(Long id);
}
