package com.back2basics.user.service.result;

import com.back2basics.company.model.Company;
import com.back2basics.user.model.User;

public record UserSimpleResult(Long id, String username, String name, String phone, String position,
                               Long companyId, String companyName) {

    public static UserSimpleResult of(User user, Company company) {
        return new UserSimpleResult(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getPhone(),
            user.getPosition(),
            company != null ? company.getId() : null,
            company != null ? company.getName() : null
        );
    }
}
