package com.back2basics.service.user.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResetPasswordCommand {

    private String email;

    @Builder
    public ResetPasswordCommand(String email) {
        this.email = email;
    }
}
