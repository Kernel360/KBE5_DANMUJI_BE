package com.back2basics.user.service.result;

import com.back2basics.user.model.Role;
import com.back2basics.user.model.User;

public record UserSummaryResult(Long id, String username, String name, Role role, String position) {

    public static UserSummaryResult from(User user) {
        return new UserSummaryResult(user.getId(), user.getUsername(), user.getName(),
            user.getRole(), user.getPosition());
    }
}
