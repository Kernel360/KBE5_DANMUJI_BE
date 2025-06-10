package com.back2basics.notify.port.in;

import com.back2basics.notify.model.NotificationType;

public interface NotifyUseCase {

    void notify(Long clientId, String message, NotificationType type);

}
