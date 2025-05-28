package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserCreateResult;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateResponse {

    private final Long id;
    private final String username;
    private final String password;

    @Builder
    public UserCreateResponse(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static UserCreateResponse from(UserCreateResult result) {
        return UserCreateResponse.builder()
            .id(result.getId())
            .username(result.getUsername())
            .password(result.getPassword())
            .build();
    }
}
