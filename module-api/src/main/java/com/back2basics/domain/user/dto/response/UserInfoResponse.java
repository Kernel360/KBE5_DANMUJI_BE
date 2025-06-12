package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserInfoResult;
import java.time.LocalDateTime;

public record UserInfoResponse(Long id, String username, String name, String email, String phone,
                               String position, LocalDateTime lastLoginAt, LocalDateTime createdAt,
                               LocalDateTime updatedAt, LocalDateTime deletedAt, Long companyId,
                               String companyName) {

    public static UserInfoResponse from(UserInfoResult result) {
        return new UserInfoResponse(result.id(), result.username(), result.name(),
            result.email(), result.phone(), result.position(), result.lastLoginAt(),
            result.CreatedAt(), result.updatedAt(), result.deletedAt(), result.companyId(),
            result.companyName());
    }
}
