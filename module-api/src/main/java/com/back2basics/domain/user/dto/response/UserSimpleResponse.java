package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserSimpleResult;

public record UserSimpleResponse(Long id, String username, String name) {

    public static UserSimpleResponse from(UserSimpleResult result) {
        return new UserSimpleResponse(result.id(), result.username(), result.name());
    }
}
