package com.back2basics.domain.user.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ChangePasswordRequest(
    @NotEmpty String currentPassword,
    @NotEmpty String newPassword) {

}
