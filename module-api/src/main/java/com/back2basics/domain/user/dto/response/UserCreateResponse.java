package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserCreateResult;

public record UserCreateResponse(Long id, String username, String password, Long companyId) {

    public static UserCreateResponse from(UserCreateResult result) {
        return new UserCreateResponse(result.getId(), result.getUsername(), result.getPassword(),
            result.getCompanyId());
    }
}
