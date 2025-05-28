package com.back2basics.user.port.in.command;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResetPasswordCommand {

    private String newPassword;

    @Builder
    public ResetPasswordCommand(String newPassword) {
        this.newPassword = newPassword;
    }
}
