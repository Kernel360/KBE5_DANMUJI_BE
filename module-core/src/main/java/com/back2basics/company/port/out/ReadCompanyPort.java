package com.back2basics.company.port.out;

import com.back2basics.company.model.Company;
import com.back2basics.company.service.result.CompanyNameResult;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadCompanyPort {

    Optional<Company> findById(Long id);

    Optional<Company> findByIdForRestore(Long id);

    Page<Company> findByNameContaining(Pageable pageable, String keyword);

    List<Company> getAllCompanies();

    List<CompanyNameResult> getAllNames();

    Page<Company> findAll(Pageable pageable);

    List<Company> getRecentCompanies();

    Long getCompanyCounts();
}
