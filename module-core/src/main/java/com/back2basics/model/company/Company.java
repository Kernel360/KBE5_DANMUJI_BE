package com.back2basics.model.company;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Company {

    private final Long companyId;
    private String name;
    private String ceoName;
    private String bio;
    private CompanyType companyType;
    private int bizNo;
    private String address;

    public enum CompanyType {
        CLIENT,
        AGENCY
    }

    @Builder
    public Company(Long companyId, String name, String ceoName, String bio, CompanyType companyType,
        int bizNo, String address) {
        this.companyId = companyId;
        this.name = name;
        this.ceoName = ceoName;
        this.bio = bio;
        this.companyType = companyType;
        this.bizNo = bizNo;
        this.address = address;
    }

}
