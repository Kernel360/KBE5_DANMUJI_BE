package com.back2basics.domain.user.dto.request;

import com.back2basics.user.port.in.command.UserCreateCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserCreateRequest(@NotEmpty String username, @NotEmpty String name,
                                @NotEmpty @Email String email, @NotEmpty String phone,
                                @NotEmpty String position) {

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
