package com.back2basics.domain.user.dto.request;

import com.back2basics.user.port.in.command.ConfirmMailCommand;
import jakarta.validation.constraints.NotNull;

public record ConfirmMailRequest(@NotNull String token, @NotNull String newPassword) {

    public ConfirmMailCommand toCommand() {
        return ConfirmMailCommand.builder()
            .token(token)
            .newPassword(newPassword)
            .build();
    }
}
