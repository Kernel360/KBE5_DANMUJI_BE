package com.back2basics.notify.port.in;

import com.back2basics.notify.port.in.command.SendNotificationCommand;

public interface NotifyUseCase {

    void notify(Long clientId, SendNotificationCommand command);

}
