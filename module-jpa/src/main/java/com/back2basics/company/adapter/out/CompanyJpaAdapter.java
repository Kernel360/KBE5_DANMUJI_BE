package com.back2basics.company.adapter.out;

import com.back2basics.company.entity.CompanyEntity;
import com.back2basics.company.mapper.CompanyMapper;
import com.back2basics.company.repository.CompanyEntityRepository;
import com.back2basics.model.company.Company;
import com.back2basics.port.out.company.CompanyRepositoryPort;
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
        CompanyEntity entity = mapper.fromDomain(company);
        return companyEntityRepository.save(entity).getCompany_id();
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
        companyEntityRepository.save(mapper.fromDomain(company));
    }

    @Override
    public void deleteById(Long id) {
        companyEntityRepository.deleteById(id);
    }
}
