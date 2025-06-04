package com.back2basics.user.port.in;

import com.back2basics.user.port.in.command.ChangePasswordCommand;

public interface ChangePasswordUseCase {

    void change(String username, ChangePasswordCommand command);
}
