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

    @Override
    public boolean existsByNameAndIdNot(String name, Long id) {
        return companyEntityRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public boolean existsByBizNoAndIdNot(Long bizNo, Long id) {
        return companyEntityRepository.existsByBizNoAndIdNot(bizNo, id);
    }

    @Override
    public boolean existsByAddressAndIdNot(String address, Long id) {
        return companyEntityRepository.existsByAddressAndIdNot(address, id);
    }

}
