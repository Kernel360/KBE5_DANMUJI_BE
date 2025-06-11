package com.back2basics.user.port.in;

import com.back2basics.user.port.in.command.UserUpdateCommand;

public interface UpdateUserUseCase {

    void update(Long userId, UserUpdateCommand command);

    void updateUserRole(Long userId, String role);

    void updateLastLoginAt(String username);
}
