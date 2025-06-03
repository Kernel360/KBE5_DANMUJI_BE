package com.back2basics.user.port.in.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateCommand {

    private String username;
    private String name;
    private String email;
    private String phone;
    private String position;
    private Long companyId;

    @Builder
    public UserCreateCommand(String username, String name,
        String email, String phone, String position, Long companyId) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.companyId = companyId;
    }
}
