package com.back2basics.service.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateCommand {

    private String name;

    private String ceoName;

    private String bio;

    private CompanyType companyType;

    private Integer bizNo;

    private String address;


    public enum CompanyType {
        CLIENT,
        AGENCY
    }


}
