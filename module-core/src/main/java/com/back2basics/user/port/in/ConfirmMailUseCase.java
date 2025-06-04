package com.back2basics.user.port.in;

import com.back2basics.user.port.in.command.ConfirmMailCommand;

public interface ConfirmMailUseCase {

    void resetPassword(ConfirmMailCommand command);
}
