package com.back2basics.user.service.result;

import com.back2basics.user.model.User;
import java.time.LocalDateTime;

public record UserInfoResult(Long id, String username, String name, String email, String phone,
                             String position, Long companyId, String companyName,
                             LocalDateTime CreatedAt, LocalDateTime updatedAt) {

    public static UserInfoResult toResult(User user) {
        return new UserInfoResult(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getPosition(),
            user.getCompanyId(),
            null,
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

}
