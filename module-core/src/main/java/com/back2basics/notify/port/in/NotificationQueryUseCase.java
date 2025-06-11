package com.back2basics.notify.port.in;

import com.back2basics.notify.service.result.NotificationResult;
import java.util.List;

public interface NotificationQueryUseCase {

    List<NotificationResult> findByClientId(Long clientId);

    long countUnreadByClientId(Long clientId);
}
