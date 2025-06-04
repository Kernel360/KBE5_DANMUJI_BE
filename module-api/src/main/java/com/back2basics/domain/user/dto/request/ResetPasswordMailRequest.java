package com.back2basics.domain.user.dto.request;


import com.back2basics.user.port.in.command.SendMailCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record ResetPasswordMailRequest(
    @NotEmpty String username, @Email @NotEmpty String email
) {

    public SendMailCommand toCommand() {
        return SendMailCommand.builder()
            .username(username)
            .email(email)
            .build();
    }
}
