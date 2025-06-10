package com.back2basics.adapter.persistence.company.adapter;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.company.CompanyMapper;
import com.back2basics.company.model.Company;
import com.back2basics.company.port.out.CreateCompanyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCompanyJpaAdapter implements CreateCompanyPort {

    private final CompanyEntityRepository companyEntityRepository;
    private final CompanyMapper mapper;

    @Override
    public Long save(Company company) {
        CompanyEntity entity = mapper.toEntity(company);
        return companyEntityRepository.save(entity).getId();
    }

    @Override
    public boolean existsByName(String name) {
        return companyEntityRepository.existsByName(name);
    }
}
