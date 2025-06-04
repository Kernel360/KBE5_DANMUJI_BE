package com.back2basics.user.service.result;

import com.back2basics.user.model.User;
import com.back2basics.user.model.UserType;
import java.time.LocalDateTime;

public record UserInfoResult(Long id, String username, String name, String email, String phone,
                             String position, UserType userType, Long companyId,
                             LocalDateTime CreatedAt, LocalDateTime updatedAt) {

    public static UserInfoResult toResult(User user) {
        return new UserInfoResult(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getPosition(),
            user.getUserType(),
            user.getCompanyId(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

}
