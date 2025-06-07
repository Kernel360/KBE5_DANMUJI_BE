package com.back2basics.user.service.result;

import com.back2basics.company.model.Company;
import com.back2basics.user.model.User;
import java.time.LocalDateTime;

public record UserInfoResult(Long id, String username, String name, String email, String phone,
                             String position, Long companyId, String companyName,
                             LocalDateTime CreatedAt, LocalDateTime updatedAt) {

    public static UserInfoResult of(User user, Company company) {
        return new UserInfoResult(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getPosition(),
            company != null ? company.getId() : null,
            company != null ? company.getName() : null,
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}
