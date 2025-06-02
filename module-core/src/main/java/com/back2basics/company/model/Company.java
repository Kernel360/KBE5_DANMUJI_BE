package com.back2basics.company.model;

import com.back2basics.company.port.in.command.UpdateCompanyCommand;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Company {

    private final Long id;
    private String name;
    private String ceoName;
    private String bio;
    private CompanyType companyType;
    private Integer bizNo;
    private String address;
    private String email;
    private String tel;

    @Builder
    public Company(Long id, String name, String ceoName, String bio, CompanyType companyType,
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

    public void update(UpdateCompanyCommand command) {
        this.name = command.getName();
        this.ceoName = command.getCeoName();
        this.bio = command.getBio();
        this.bizNo = command.getBizNo();
        this.companyType = command.getCompanyType();
        this.address = command.getAddress();
        this.email = command.getEmail();
        this.tel = command.getTel();
    }

}
