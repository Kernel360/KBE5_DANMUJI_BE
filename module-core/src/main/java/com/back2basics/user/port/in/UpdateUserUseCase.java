package com.back2basics.user.port.in;

import com.back2basics.user.port.in.command.UserUpdateCommand;

public interface UpdateUserUseCase {

    void update(Long userId, UserUpdateCommand command, Long loggedInUserId);

    void updateUserRole(Long userId, String role, Long loggedInUserId);

    void updateLastLoginAt(String username);
}
