package com.back2basics.service.company.dto;

import com.back2basics.company.model.Company;
import com.back2basics.company.model.CompanyType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CompanyResponseDto {

    private final Long id;
    private final String name;
    private final String ceoName;
    private final String bio;
    private final CompanyType companyType;
    private final Integer bizNo;
    private final String address;
    private final String email;
    private final String tel;

    @Builder
    public CompanyResponseDto(Long id, String name, String ceoName, String bio,
        CompanyType companyType,
        Integer bizNo, String address, String email, String tel) {
        this.id = id;
        this.name = name;
        this.ceoName = ceoName;
        this.bio = bio;
        this.companyType = companyType;
        this.bizNo = bizNo;
        this.address = address;
        this.email = email;
        this.tel = tel;
    }

    public static CompanyResponseDto from(Company company) {
        return CompanyResponseDto.builder()
            .id(company.getId())
            .name(company.getName())
            .ceoName(company.getCeoName())
            .bio(company.getBio())
            .companyType(company.getCompanyType())
            .bizNo(company.getBizNo())
            .address(company.getAddress())
            .build();
    }
}
