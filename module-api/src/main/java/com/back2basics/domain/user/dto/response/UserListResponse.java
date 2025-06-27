package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserListResult;
import java.time.LocalDateTime;

public record UserListResponse(Long id, String username, String name, String email,
                               String phone, String position, LocalDateTime createdAt) {

    public static UserListResponse from(UserListResult result) {
        return new UserListResponse(result.id(), result.username(), result.name(),
            result.email(), result.phone(), result.position(), result.createdAt());
    }

}
