package com.back2basics.adapter.persistence.company;

import com.back2basics.company.port.out.CompanyRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyJpaAdapter implements CompanyRepositoryPort {

    private final CompanyEntityRepository companyEntityRepository;
    private final CompanyMapper mapper;


    @Override
    public void deleteById(Long id) {
        companyEntityRepository.deleteById(id);
    }
}
