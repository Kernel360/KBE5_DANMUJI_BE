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
        if (command.getName() != null) {
            this.name = command.getName();
        }
        if (command.getCeoName() != null) {
            this.ceoName = command.getCeoName();
        }
        if (command.getBio() != null) {
            this.bio = command.getBio();
        }
        if (command.getBizNo() != null) {
            this.bizNo = command.getBizNo();
        }
        if (command.getCompanyType() != null) {
            this.companyType = command.getCompanyType();
        }
        if (command.getAddress() != null) {
            this.address = command.getAddress();
        }
        if (command.getEmail() != null) {
            this.email = command.getEmail();
        }
        if (command.getTel() != null) {
            this.tel = command.getTel();
        }
    }

}
