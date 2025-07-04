package com.back2basics.adapter.persistence.company.adapter;

import static com.back2basics.infra.exception.company.CompanyErrorCode.COMPANY_NOT_FOUND;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.company.CompanyMapper;
import com.back2basics.company.model.Company;
import com.back2basics.company.port.out.RestoreCompanyPort;
import com.back2basics.infra.exception.company.CompanyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyRestoreJpaAdapter implements RestoreCompanyPort {
    private final CompanyEntityRepository companyEntityRepository;
    private final ReadCompanyJpaAdapter readCompanyJpaAdapter;
    private final CompanyMapper mapper;

    @Override
    public void restoreCompany(Company company) {
        Company deletedCompany = readCompanyJpaAdapter.findByIdForRestore(company.getId())
            .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        CompanyEntity entity = mapper.toEntity(deletedCompany);
        entity.restore();

        companyEntityRepository.save(entity);
    }
}

