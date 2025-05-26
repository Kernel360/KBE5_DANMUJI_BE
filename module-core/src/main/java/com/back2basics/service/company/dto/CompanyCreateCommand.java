package com.back2basics.service.company.dto;

import com.back2basics.model.company.Company.CompanyType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyCreateCommand {

    private String name;
    private String ceoName;
    private String bio;
    private CompanyType companyType;
    private String bizNo;
    private String address;

    public CompanyCreateCommand(String name, String ceoName, String bio, CompanyType companyType,
        String bizNo, String address) {
        this.name = name;
        this.ceoName = ceoName;
        this.bio = bio;
        this.companyType = companyType;
        this.bizNo = bizNo;
        this.address = address;
    }

}
