package com.back2basics.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserPasswordChangeRequest {

    @NotNull
    private String currentPassword;

    @NotNull
    private String newPassword;

}
