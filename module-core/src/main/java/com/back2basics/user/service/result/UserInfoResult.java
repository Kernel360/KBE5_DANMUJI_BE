package com.back2basics.user.service.result;

import com.back2basics.user.model.UserType;
import java.time.LocalDateTime;

public record UserInfoResult(Long id, String username, String name, String email, String phone,
                             String position, UserType userType, Long companyId,
                             LocalDateTime CreatedAt, LocalDateTime updatedAt) {

    public static UserInfoResult toResult(User user) {
        return UserInfoResult.builder()
            .id(user.getId())
            .username(user.getUsername())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .position(user.getPosition())
            .companyId(user.getCompanyId())
            .build();
    }

}
