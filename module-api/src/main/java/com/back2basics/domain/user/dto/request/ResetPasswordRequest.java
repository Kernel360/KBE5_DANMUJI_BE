package com.back2basics.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record ResetPasswordRequest(
    @Email
    @NotEmpty
    String email
) {

}
