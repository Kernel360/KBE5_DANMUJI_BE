package com.back2basics.adapter.persistence.company;

import com.back2basics.company.model.Company;
import com.back2basics.company.port.out.CompanyRepositoryPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyJpaAdapter implements CompanyRepositoryPort {

    private final CompanyEntityRepository companyEntityRepository;
    private final CompanyMapper mapper;

    @Override
    public Long save(Company company) {
        CompanyEntity entity = mapper.toEntity(company);
        return companyEntityRepository.save(entity).getId();
    }

    @Override
    public Optional<Company> findById(Long id) {
        return companyEntityRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Company> findAll() {
        return companyEntityRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void update(Company company) {
        companyEntityRepository.save(mapper.toEntity(company));
    }

    @Override
    public void deleteById(Long id) {
        companyEntityRepository.deleteById(id);
    }
}
