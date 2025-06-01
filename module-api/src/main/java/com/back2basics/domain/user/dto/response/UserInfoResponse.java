package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserInfoResult;

public record UserInfoResponse(Long id, String username, String name, String email, String phone,
                               String position, Long companyId) {

    public static UserInfoResponse from(UserInfoResult result) {
        return new UserInfoResponse(result.getId(), result.getUsername(), result.getName(),
            result.getEmail(),
            result.getPhone(), result.getPosition(), result.getCompanyId());
    }
}
