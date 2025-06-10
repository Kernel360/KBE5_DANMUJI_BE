package com.back2basics.domain.user.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(
    @NotEmpty String currentPassword,
    @NotEmpty
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
        message = "비밀번호는 최소 8자, 하나 이상의 문자, 숫자, 특수문자를 포함해야 합니다."
    ) String newPassword) {

}
