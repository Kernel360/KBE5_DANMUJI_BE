package com.back2basics.domain.company.dto.request;

import com.back2basics.company.port.in.command.UpdateCompanyCommand;
import com.back2basics.custom.CustomNotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateCompanyRequest {

    @CustomNotBlank(message = "회사 이름은 공백일 수 없습니다.")
    private String name;
    @CustomNotBlank(message = "대표자 이름은 공백일 수 없습니다.")
    private String ceoName;
    @CustomNotBlank(message = "회사 소개는 공백일 수 없습니다.")
    private String bio;
    @Pattern(regexp = "\\d{10}", message = "사업자등록번호는 숫자 10자리여야 합니다.")
    private String bizNo;
    @CustomNotBlank(message = "우편 번호는 공백일 수 없습니다.")
    private String zonecode;
    @CustomNotBlank(message = "회사 주소는 공백일 수 없습니다.")
    private String address;
    @CustomNotBlank(message = "회사 이메일 주소는 공백일 수 없습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @CustomNotBlank(message = "회사 전화번호는 공백일 수 없습니다.")
    @Pattern(
        regexp = "^\\d{9,11}$",
        message = "전화번호는 숫자만 포함하여 9~11자리여야 합니다."
    )
    private String tel;

    public UpdateCompanyCommand toCommand() {
        return UpdateCompanyCommand.builder()
            .name(name)
            .ceoName(ceoName)
            .bio(bio)
            .bizNo(Long.parseLong(bizNo))
            .zonecode(zonecode)
            .address(address)
            .email(email)
            .tel(tel)
            .build();
    }

}
