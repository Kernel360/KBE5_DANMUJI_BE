package com.back2basics.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ResetPasswordRequest {

    @Email
    @NotEmpty
    private String email;

}
