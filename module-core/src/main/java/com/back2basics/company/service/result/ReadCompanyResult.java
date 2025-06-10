package com.back2basics.company.service.result;

import com.back2basics.company.model.Company;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadCompanyResult {

    private final Long id;
    private final String name;
    private final String ceoName;
    private final String bio;
    private final Long bizNo;
    private final String address;
    private final String email;
    private final String tel;

    @Builder
    public ReadCompanyResult(Long id, String name, String ceoName, String bio,
        Long bizNo, String address, String email, String tel) {
        this.id = id;
        this.name = name;
        this.ceoName = ceoName;
        this.bio = bio;
        this.bizNo = bizNo;
        this.address = address;
        this.email = email;
        this.tel = tel;
    }

    public static ReadCompanyResult toResult(Company company) {
        return ReadCompanyResult.builder()
            .id(company.getId())
            .name(company.getName())
            .ceoName(company.getCeoName())
            .bio(company.getBio())
            .bizNo(company.getBizNo())
            .address(company.getAddress())
            .email(company.getEmail())
            .tel(company.getTel())
            .build();
    }
}
