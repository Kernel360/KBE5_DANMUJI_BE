package com.back2basics.user.port.in.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangePasswordCommand {

    private String currentPassword;
    private String newPassword;

    @Builder
    public ChangePasswordCommand(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
