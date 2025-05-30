package com.back2basics.adapter.persistence.company.adapter;

import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.company.CompanyMapper;
import com.back2basics.company.model.Company;
import com.back2basics.company.port.out.ReadCompanyPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadCompanyJpaAdapter implements ReadCompanyPort {

    private final CompanyEntityRepository companyEntityRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Optional<Company> findById(Long id) {
        return companyEntityRepository.findById(id).map(companyMapper::toDomain);
    }

    @Override
    public List<Company> findAll() {
        return companyEntityRepository.findAll().stream()
            .map(companyMapper::toDomain)
            .collect(Collectors.toList());
    }

}
