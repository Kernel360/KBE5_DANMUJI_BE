package com.back2basics.model.company;

import com.back2basics.service.company.dto.CompanyUpdateCommand;
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

    @Builder
    public Company(Long id, String name, String ceoName, String bio, CompanyType companyType,
        Integer bizNo, String address) {
        this.id = id;
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
        if (command.getBizNo() != null) {
            this.bizNo = command.getBizNo();
        }
        if (command.getCompanyType() != null) {
            this.companyType = CompanyType.valueOf(command.getCompanyType());
        }
        if (command.getAddress() != null) {
            this.address = command.getAddress();
        }
    }

}
