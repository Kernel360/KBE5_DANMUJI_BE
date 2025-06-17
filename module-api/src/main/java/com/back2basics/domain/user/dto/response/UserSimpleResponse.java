package com.back2basics.domain.user.dto.response;

import com.back2basics.user.service.result.UserSimpleResult;
import java.time.LocalDateTime;

public record UserSimpleResponse(Long id, String username, String name, String phone,
                                 String position, Long companyId, String companyName,
                                 LocalDateTime createdAt, LocalDateTime updatedAt,
                                 LocalDateTime deletedAt) {

    public static UserSimpleResponse from(UserSimpleResult result) {
        return new UserSimpleResponse(result.id(), result.username(), result.name()
            , result.phone(), result.position(), result.companyId(), result.companyName(),
            result.createdAt(), result.updatedAt(), result.deletedAt());
    }
}
