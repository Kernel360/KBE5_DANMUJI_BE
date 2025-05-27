package com.back2basics.port.in.user;

import com.back2basics.service.user.command.UserUpdateCommand;

public interface UpdateUserUseCase {

    void updateUser(Long userId, UserUpdateCommand command);
}
