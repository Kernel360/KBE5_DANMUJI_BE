package com.back2basics.notify.port.in;

import com.back2basics.notify.service.result.NotificationResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationQueryUseCase {

    Page<NotificationResult> findByClientId(Long clientId, Pageable pageable);

    long countUnreadByClientId(Long clientId);
}
