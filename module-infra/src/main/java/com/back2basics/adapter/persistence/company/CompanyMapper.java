package com.back2basics.adapter.persistence.company;

import com.back2basics.company.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company toDomain(CompanyEntity entity) {
        if (entity == null) {
            return null;
        }
        return Company.builder()
            .id(entity.getId())
            .name(entity.getName())
            .ceoName(entity.getCeoName())
            .bio(entity.getBio())
            .bizNo(entity.getBizNo())
            .zonecode(entity.getZonecode())
            .address(entity.getAddress())
            .email(entity.getEmail())
            .tel(entity.getTel())
            .createdAt(entity.getCreatedAt())
            .build();
    }

    public CompanyEntity toEntity(Company domain) {
        if (domain == null) {
            return null;
        }
        return CompanyEntity.builder()
            .id(domain.getId())
            .name(domain.getName())
            .ceoName(domain.getCeoName())
            .bio(domain.getBio())
            .bizNo(domain.getBizNo())
            .zonecode(domain.getZonecode())
            .address(domain.getAddress())
            .email(domain.getEmail())
            .tel(domain.getTel())
            .build();
    }

}
