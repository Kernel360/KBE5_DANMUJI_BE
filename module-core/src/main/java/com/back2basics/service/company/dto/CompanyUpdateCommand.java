package com.back2basics.service.company.dto;

import com.back2basics.infra.post.custom.CustomEnumCheck;
import com.back2basics.infra.post.custom.CustomTitleNotBlank;
import com.back2basics.model.company.Company;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateCommand {

    @CustomTitleNotBlank(message = "회사명은 공백일 수 없습니다.")
    private String name;

    private String ceoName;

    @Size(min = 1, message = "내용은 공백일 수 없습니다.")
    private String bio;

    @CustomEnumCheck(enumClass = CompanyType.class, message = "올바른 enum type이 아닙니다")
    private Company.CompanyType companyType;

    private String bizNo;

    private String address;


    public enum CompanyType {
        CLIENT,
        AGENCY
    }


}
