package com.back2basics.user.service.result;

import com.back2basics.user.model.User;
import java.time.LocalDateTime;

public record UserListResult(Long id, String username, String name, String email,
                             String phone, String position, LocalDateTime createdAt) {

    public static UserListResult from(User user) {
        return new UserListResult(user.getId(), user.getUsername(), user.getName(),
            user.getEmail(), user.getPhone(), user.getPosition(), user.getCreatedAt());
    }

}
