package com.back2basics.service.company.dto;

import com.back2basics.model.company.Company;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CompanyResponseDto {

    private final Long companyId;
    private final String name;
    private final String ceoName;
    private final String bio;
    private final Company.CompanyType companyType;
    // TODO : bizNo의 타입 생각해보기
    private final int bizNo;
    private final String address;

    public enum CompanyType {
        CLIENT,
        AGENCY
    }

    @Builder
    public CompanyResponseDto(Long companyId, String name, String ceoName, String bio,
        Company.CompanyType companyType,
        int bizNo, String address) {
        this.companyId = companyId;
        this.name = name;
        this.ceoName = ceoName;
        this.bio = bio;
        this.companyType = companyType;
        this.bizNo = bizNo;
        this.address = address;
    }

    public static CompanyResponseDto from(Company company) {
        return CompanyResponseDto.builder()
            .companyId(company.getCompanyId())
            .name(company.getName())
            .ceoName(company.getCeoName())
            .bio(company.getBio())
            .companyType(company.getCompanyType())
            .bizNo(company.getBizNo())
            .address(company.getAddress())
            .build();
    }
}
