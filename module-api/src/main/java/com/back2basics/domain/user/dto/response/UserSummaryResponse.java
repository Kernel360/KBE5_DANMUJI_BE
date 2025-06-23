package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserSummaryResult;

public record UserSummaryResponse(Long id, String username, String name, String position) {

    public static UserSummaryResponse from(UserSummaryResult result) {
        return new UserSummaryResponse(result.id(), result.username(), result.name(), result.position());
    }
}
