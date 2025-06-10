package com.back2basics.user.service.result;

import com.back2basics.company.model.Company;
import com.back2basics.user.model.User;
import java.time.LocalDateTime;

public record UserSimpleResult(Long id, String username, String name, String phone, String position,
                               LocalDateTime createdAt, LocalDateTime updatedAt,
                               LocalDateTime deletedAt, Long companyId, String companyName) {

    public static UserSimpleResult of(User user, Company company) {
        return new UserSimpleResult(
            user.getId(),
            user.getUsername(),
            user.getName(),
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
