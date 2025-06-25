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
    public Company save(Company company) {
        CompanyEntity entity = mapper.toEntity(company);
        CompanyEntity saved = companyEntityRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public boolean existsByName(String name) {
        return companyEntityRepository.existsByName(name);
    }

    @Override
    public boolean existsByBizNo(Long bizNo) {
        return companyEntityRepository.existsByBizNo(bizNo);
    }

    @Override
    public boolean existsByAddress(String addr) {
        return companyEntityRepository.existsByAddress(addr);
    }
}
