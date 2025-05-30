package com.back2basics.adapter.persistence.company.adapter;

import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.company.CompanyMapper;
import com.back2basics.company.model.Company;
import com.back2basics.company.port.out.UpdateCompanyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCompanyJpaAdapter implements UpdateCompanyPort {

    private final CompanyEntityRepository companyEntityRepository;
    private final CompanyMapper companyMapper;

    @Override
    public void update(Company company) {
        companyEntityRepository.save(companyMapper.toEntity(company));
    }

}
