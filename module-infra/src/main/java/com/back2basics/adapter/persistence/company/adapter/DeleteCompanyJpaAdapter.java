package com.back2basics.adapter.persistence.company.adapter;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.company.model.Company;
import com.back2basics.company.port.out.DeleteCompanyPort;
import com.back2basics.infra.exception.company.CompanyErrorCode;
import com.back2basics.infra.exception.company.CompanyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCompanyJpaAdapter implements DeleteCompanyPort {

    private final CompanyEntityRepository companyEntityRepository;

    public void softDelete(Company company) {
        CompanyEntity companyEntity = companyEntityRepository.findById(company.getId())
            .orElseThrow(() -> new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND));

        companyEntity.markDeleted();
        companyEntityRepository.save(companyEntity);
    }

}
