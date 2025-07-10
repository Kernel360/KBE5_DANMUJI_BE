package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserPositionResult;

public record UserPositionResponse(String position) {

    public static UserPositionResponse from(UserPositionResult result) {
        return new UserPositionResponse(result.position());
    }
}
