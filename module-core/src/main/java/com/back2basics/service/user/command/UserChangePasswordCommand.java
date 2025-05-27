package com.back2basics.service.user.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserChangePasswordCommand {

    private String currentPassword;
    private String newPassword;

    @Builder
    public UserChangePasswordCommand(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
