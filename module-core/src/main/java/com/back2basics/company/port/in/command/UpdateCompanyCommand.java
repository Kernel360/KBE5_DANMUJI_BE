package com.back2basics.company.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCompanyCommand {

    private String name;
    private String ceoName;
    private String bio;
    private Long bizNo;
    private String zoneCode;
    private String address;
    private String email;
    private String tel;

}
