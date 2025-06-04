package com.back2basics.user.port.in;

import com.back2basics.user.port.in.command.SendMailCommand;

public interface SendMailUseCase {

    void sendResetLink(SendMailCommand command);
}
