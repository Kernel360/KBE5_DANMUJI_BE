package com.back2basics.service.user.dto;

import com.back2basics.model.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String username;

    @Builder
    public UserResponseDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .build();
    }
}
