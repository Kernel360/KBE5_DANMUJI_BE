package com.back2basics.adapter.persistence.company;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.out.CompanyCreatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCompanyJpaAdapter implements CompanyCreatePort {

    private final CompanyEntityRepository companyEntityRepository;
    private final CompanyMapper mapper;

    @Override
    public Long save(Company company) {
        CompanyEntity entity = mapper.toEntity(company);
        return companyEntityRepository.save(entity).getId();
    }
}
