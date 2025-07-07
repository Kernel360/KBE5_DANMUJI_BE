package com.back2basics.company.port.out;

import com.back2basics.company.model.Company;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadCompanyPort {

    Optional<Company> findById(Long id);

    Page<Company> findByNameContaining(Pageable pageable, String keyword);

    List<Company> getAllCompanies();

    Page<Company> findAll(Pageable pageable);

    List<Company> getRecentCompanies();

    Long getCompanyCounts();
}
