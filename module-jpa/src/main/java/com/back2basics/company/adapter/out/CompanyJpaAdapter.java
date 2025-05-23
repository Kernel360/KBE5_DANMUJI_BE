package com.back2basics.company.adapter.out;

import com.back2basics.company.entity.CompanyEntity;
import com.back2basics.company.mapper.CompanyMapper;
import com.back2basics.company.repository.CompanyEntityRepository;
import com.back2basics.model.company.Company;
import com.back2basics.port.out.company.CompanyRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyJpaAdapter implements CompanyRepositoryPort {

    private final CompanyEntityRepository companyEntityRepository;
    private final CompanyMapper mapper;

    @Override
    public Long save(Company company) {
        CompanyEntity entity = mapper.fromDomain(company);
        return companyEntityRepository.save(entity).getCompany_id();
    }

    @Override
    public void deleteById(Long id) {
        companyEntityRepository.deleteById(id);
    }
}
