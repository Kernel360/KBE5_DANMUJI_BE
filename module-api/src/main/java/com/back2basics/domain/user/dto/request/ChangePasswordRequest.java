package com.back2basics.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {

    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;

}
