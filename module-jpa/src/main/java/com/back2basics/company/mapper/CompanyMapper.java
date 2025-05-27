package com.back2basics.company.mapper;

import com.back2basics.company.entity.CompanyEntity;
import com.back2basics.model.company.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company toDomain(CompanyEntity entity) {
        return Company.builder()
            .id(entity.getId())
            .name(entity.getName())
            .ceoName(entity.getCeoName())
            .bio(entity.getBio())
            .companyType(entity.getCompanyType())
            .bizNo(entity.getBizNo())
            .address(entity.getAddress())
            .email(entity.getEmail())
            .tel(entity.getTel())
            .build();
    }

    public CompanyEntity toEntity(Company domain) {
        return CompanyEntity.builder()
            .id(domain.getId())
            .name(domain.getName())
            .ceoName(domain.getCeoName())
            .bio(domain.getBio())
            .companyType(domain.getCompanyType())
            .bizNo(domain.getBizNo())
            .address(domain.getAddress())
            .email(domain.getEmail())
            .tel(domain.getTel())
            .build();
    }

}
