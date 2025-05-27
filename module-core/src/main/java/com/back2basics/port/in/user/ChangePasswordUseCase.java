package com.back2basics.port.in.user;

import com.back2basics.service.user.command.UserChangePasswordCommand;

public interface ChangePasswordUseCase {

    void changePassword(Long userId, UserChangePasswordCommand command);
}
