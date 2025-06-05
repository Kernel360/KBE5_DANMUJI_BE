package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserSimpleResult;

public record UserSimpleResponse(Long id, String username, String name, String phone,
                                 String position,
                                 Long companyId, String companyName) {

    public static UserSimpleResponse from(UserSimpleResult result) {
        return new UserSimpleResponse(result.id(), result.username(), result.name()
            , result.phone(), result.position(), result.companyId(), result.companyName());
    }
}
