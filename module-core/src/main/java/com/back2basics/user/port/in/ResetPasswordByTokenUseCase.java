package com.back2basics.user.port.in;

import com.back2basics.user.port.in.command.ResetPasswordByTokenCommand;

public interface ResetPasswordByTokenUseCase {

    void change(ResetPasswordByTokenCommand command);
}
