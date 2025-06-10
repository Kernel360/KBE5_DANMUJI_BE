package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserInfoResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record UserInfoResponse(Long id, String username, String name, String email, String phone,
                               String position,
                               @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
                               @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedAt,
                               @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime deletedAt,
                               Long companyId, String companyName) {

    public static UserInfoResponse from(UserInfoResult result) {
        return new UserInfoResponse(result.id(), result.username(), result.name(),
            result.email(), result.phone(), result.position(), result.CreatedAt(),
            result.updatedAt(), result.deletedAt(), result.companyId(), result.companyName());
    }
}
