package com.back2basics.domain.user.dto.request;

import com.back2basics.user.port.in.command.ResetPasswordByTokenCommand;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordByTokenRequest(@NotNull String token, @NotNull String newPassword) {

    public ResetPasswordByTokenCommand toCommand() {
        return ResetPasswordByTokenCommand.builder()
            .token(token)
            .newPassword(newPassword)
            .build();
    }
}
