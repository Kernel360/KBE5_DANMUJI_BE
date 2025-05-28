package com.back2basics.user.port.in;

import com.back2basics.user.port.in.command.ResetPasswordCommand;

public interface ResetPasswordUseCase {

    void reset(Long userId, ResetPasswordCommand command);
}
