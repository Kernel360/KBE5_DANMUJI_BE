package com.back2basics.domain.user.dto.request;

import com.back2basics.user.port.in.command.UserUpdateCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(@NotEmpty
                                @Pattern(
                                    regexp = "^[a-zA-Z0-9_]{4,20}$",
                                    message = "아이디는 영문자, 숫자, 밑줄(_)만 가능하며 4~20자여야 합니다."
                                ) String username,
                                @NotEmpty(message = "이름은 필수입니다.")
                                @Size(min = 2, max = 30, message = "이름은 2자 이상 30자 이하로 입력해주세요.") String name,

                                @NotEmpty(message = "이메일은 필수입니다.")
                                @Email(message = "유효한 이메일 형식이 아닙니다.") String email,
                                @NotEmpty(message = "전화번호는 필수입니다.")
                                @Pattern(
                                    regexp = "^\\d{9,11}$",
                                    message = "전화번호는 숫자만 포함하여 10~11자리여야 합니다."
                                ) String phone,
                                @Size(max = 50, message = "직책은 최대 50자까지 가능합니다.") String position,
                                Long companyId) {

    public UserUpdateCommand toCommand() {
        return UserUpdateCommand.builder()
            .username(username)
            .name(name)
            .email(email)
            .phone(phone)
            .position(position)
            .companyId(companyId)
            .build();
    }
}
