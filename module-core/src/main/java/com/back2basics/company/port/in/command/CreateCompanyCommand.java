package com.back2basics.company.port.in.command;

import com.back2basics.company.model.CompanyType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCompanyCommand {

    private String name;
    private String ceoName;
    private String bio;
    private CompanyType companyType;
    private Integer bizNo;
    private String address;
    private String email;
    private String tel;

}
