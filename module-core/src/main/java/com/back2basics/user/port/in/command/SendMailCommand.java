package com.back2basics.user.port.in.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendMailCommand {

    private String username;
    private String email;

    @Builder
    public SendMailCommand(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
