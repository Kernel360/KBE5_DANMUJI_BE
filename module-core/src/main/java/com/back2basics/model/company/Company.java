package com.back2basics.model.company;

import com.back2basics.service.company.dto.CompanyUpdateCommand;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Company {

    private final Long companyId;
    private String name;
    private String ceoName;
    private String bio;
    private CompanyType companyType;
    private String bizNo;
    private String address;

    public enum CompanyType {
        CLIENT,
        AGENCY
    }

    @Builder
    public Company(Long companyId, String name, String ceoName, String bio, CompanyType companyType,
        String bizNo, String address) {
        this.companyId = companyId;
        this.name = name;
        this.ceoName = ceoName;
        this.bio = bio;
        this.companyType = companyType;
        this.bizNo = bizNo;
        this.address = address;
    }

    public void update(CompanyUpdateCommand command) {
        if (command.getName() != null) {
            this.name = command.getName();
        }
        if (command.getCeoName() != null) {
            this.ceoName = command.getCeoName();
        }
        if (command.getBio() != null) {
            this.bio = command.getBio();
        }
        if (command.getCompanyType() != null) {
            this.companyType = command.getCompanyType();
        }
        if (command.getBizNo() != null) {
            this.bizNo = command.getBizNo();
        }
        if (command.getAddress() != null) {
            this.address = command.getAddress();
        }
    }

}
