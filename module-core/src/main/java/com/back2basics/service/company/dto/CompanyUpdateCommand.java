package com.back2basics.service.company.dto;

import com.back2basics.company.model.CompanyType;
import com.back2basics.infra.validation.custom.CustomEnumCheck;
import com.back2basics.infra.validation.custom.CustomNotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateCommand {

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

    @CustomNotBlank(message = "회사 주소는 공백일 수 없습니다.")
    private String email;

    @CustomNotBlank(message = "회사 주소는 공백일 수 없습니다.")
    private String tel;

}
