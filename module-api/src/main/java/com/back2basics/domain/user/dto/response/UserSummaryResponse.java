package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserSummaryResult;

public record UserSummaryResponse(Long id, String name) {

    public static UserSummaryResponse from(UserSummaryResult result) {
        return new UserSummaryResponse(result.id(), result.name());
    }
}
