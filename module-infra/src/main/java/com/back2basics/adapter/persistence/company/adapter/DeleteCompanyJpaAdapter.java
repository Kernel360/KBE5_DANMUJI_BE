package com.back2basics.adapter.persistence.company.adapter;

import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.company.port.out.DeleteCompanyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCompanyJpaAdapter implements DeleteCompanyPort {

    private final CompanyEntityRepository companyEntityRepository;

    @Override
    public void deleteById(Long id) {
        companyEntityRepository.deleteById(id);
    }

}
