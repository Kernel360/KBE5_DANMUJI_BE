package com.back2basics.user.port.in.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResetPasswordByTokenCommand {

    String token;
    String newPassword;

    @Builder
    public ResetPasswordByTokenCommand(String token, String newPassword) {
        this.token = token;
        this.newPassword = newPassword;
    }
}
