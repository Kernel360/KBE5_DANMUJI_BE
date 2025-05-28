package com.back2basics.domain.user.dto.request;

import com.back2basics.user.port.in.command.UserCreateCommand;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserCreateRequest {

    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String position;

    public UserCreateCommand toCommand() {
        return UserCreateCommand.builder()
            .username(username)
            .name(name)
            .email(email)
            .phone(phone)
            .position(position)
            .build();
    }
}
