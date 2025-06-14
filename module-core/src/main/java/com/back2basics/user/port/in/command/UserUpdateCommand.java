package com.back2basics.user.port.in.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateCommand {

    private String username;
    private String name;
    private String email;
    private String phone;
    private String position;
    private Long companyId;

    @Builder
    public UserUpdateCommand(String username, String name, String email,
        String phone, String position, Long companyId) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.companyId = companyId;
    }
}
