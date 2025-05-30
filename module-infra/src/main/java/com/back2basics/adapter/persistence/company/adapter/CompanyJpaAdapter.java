<<<<<<<< HEAD:module-infra/src/main/java/com/back2basics/adapter/persistence/company/adapter/CompanyJpaAdapter.java
package com.back2basics.adapter.persistence.company.adapter;
========
package com.back2basics.adapter.persistence.company;
>>>>>>>> 5d1ab33d6cebe93e15893a9398c646a31e935e34:module-infra/src/main/java/com/back2basics/adapter/persistence/company/CompanyJpaAdapter.java

import com.back2basics.company.model.Company;
import com.back2basics.company.port.out.CompanyRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyJpaAdapter implements CompanyRepositoryPort {

    private final CompanyEntityRepository companyEntityRepository;
    private final CompanyMapper mapper;

    @Override
    public void update(Company company) {
        companyEntityRepository.save(mapper.toEntity(company));
    }

    @Override
    public void deleteById(Long id) {
        companyEntityRepository.deleteById(id);
    }
}
