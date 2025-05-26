package com.back2basics.service.user.dto;

import com.back2basics.model.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final String phone;
    private final String position;

    @Builder
    public UserResponseDto(Long id, String username, String name, String email, String phone,
        String position) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
    }

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .position(user.getPosition())
            .build();
    }
}
