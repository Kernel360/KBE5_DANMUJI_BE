package com.back2basics.domain.user.dto.request;

import com.back2basics.user.port.in.command.UserUpdateCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserUpdateRequest(@NotEmpty String username, @NotEmpty String name,
                                @NotEmpty @Email String email,
                                @NotEmpty String phone,
                                @NotEmpty String position,
                                Long companyId) {

    public UserUpdateCommand toCommand() {
        return UserUpdateCommand.builder()
            .username(username)
            .name(name)
            .email(email)
            .phone(phone)
            .position(position)
            .companyId(companyId)
            .build();
    }
}
