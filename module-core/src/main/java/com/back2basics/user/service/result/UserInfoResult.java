package com.back2basics.user.service.result;

import com.back2basics.company.model.Company;
import com.back2basics.user.model.User;
import java.time.LocalDateTime;

public record UserInfoResult(Long id, String username, String name, String email, String phone,
                             String position, LocalDateTime CreatedAt, LocalDateTime updatedAt,
                             LocalDateTime deletedAt, Long companyId, String companyName) {

    public static UserInfoResult of(User user, Company company) {
        return new UserInfoResult(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getPosition(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.getDeletedAt(),
            company != null ? company.getId() : null,
            company != null ? company.getName() : null
        );
    }
}
