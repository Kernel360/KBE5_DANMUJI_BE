package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserCreateResult;

public record UserCreateResponse(Long id, String username, String password) {

    public static UserCreateResponse from(UserCreateResult result) {
        return new UserCreateResponse(result.id(), result.username(), result.password());
    }
}
