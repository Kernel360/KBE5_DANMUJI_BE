package com.back2basics.admin.dto.response;

import com.back2basics.service.user.result.UserInfoResult;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoResponse {

    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final String phone;
    private final String position;

    @Builder
    public UserInfoResponse(Long id, String username, String name, String email, String phone,
        String position) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
    }

    public static UserInfoResponse from(UserInfoResult result) {
        return UserInfoResponse.builder()
            .id(result.getId())
            .username(result.getUsername())
            .name(result.getName())
            .email(result.getEmail())
            .phone(result.getPhone())
            .position(result.getPosition())
            .build();

    }
}
