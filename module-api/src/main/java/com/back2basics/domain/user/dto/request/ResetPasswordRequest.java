package com.back2basics.domain.user.dto.request;


import jakarta.validation.constraints.NotEmpty;

public record ResetPasswordRequest(
    @NotEmpty
    String newPassword
) {

}
