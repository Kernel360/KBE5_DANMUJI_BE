package com.back2basics.company.mapper;

import com.back2basics.company.entity.CompanyEntity;
import com.back2basics.model.company.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company toDomain(CompanyEntity entity) {
        return Company.builder()
            .companyId(entity.getCompany_id())
            .name(entity.getName())
            .ceoName(entity.getCeoName())
            .bio(entity.getBio())
            .companyType(entity.getCompanyType())
            .bizNo(entity.getBizNo())
            .address(entity.getAddress())
            .build();
    }

    public CompanyEntity fromDomain(Company domain) {
        return CompanyEntity.builder()
            .companyId(domain.getCompanyId())
            .name(domain.getName())
            .ceoName(domain.getCeoName())
            .bio(domain.getBio())
            .companyType(domain.getCompanyType())
            .bizNo(domain.getBizNo())
            .address(domain.getAddress())
            .build();
    }

}
