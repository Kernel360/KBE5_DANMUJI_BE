package com.back2basics.company.port.in.command;

import com.back2basics.company.model.CompanyType;
import com.back2basics.infra.validation.custom.CustomEnumCheck;
import com.back2basics.infra.validation.custom.CustomNotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyCreateCommand {

    @CustomNotBlank(message = "회사 이름은 공백일 수 없습니다.")
    private String name;
    @CustomNotBlank(message = "대표자 이름은 공백일 수 없습니다.")
    private String ceoName;
    @CustomNotBlank(message = "회사 소개는 공백일 수 없습니다.")
    private String bio;
    @CustomEnumCheck(enumClass = CompanyType.class, message = "올바른 enum type이 아닙니다")
    private CompanyType companyType;
    private Integer bizNo;
    @CustomNotBlank(message = "회사 주소는 공백일 수 없습니다.")
    private String address;
    @CustomNotBlank(message = "회사 이메일 주소는 공백일 수 없습니다.")
    private String email;
    @CustomNotBlank(message = "회사 전화번호는 공백일 수 없습니다.")
    private String tel;

    public CompanyCreateCommand(String name, String ceoName, String bio, CompanyType companyType,
        Integer bizNo, String address, String email, String tel) {
        this.name = name;
        this.ceoName = ceoName;
        this.bio = bio;
        this.companyType = companyType;
        this.bizNo = bizNo;
        this.address = address;
        this.email = email;
        this.tel = tel;
    }

}
