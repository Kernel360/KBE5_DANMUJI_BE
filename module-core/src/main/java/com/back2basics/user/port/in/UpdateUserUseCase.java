package com.back2basics.user.port.in;

import com.back2basics.user.port.in.command.UserUpdateCommand;

public interface UpdateUserUseCase {

    void updateUser(Long userId, UserUpdateCommand command);
}
