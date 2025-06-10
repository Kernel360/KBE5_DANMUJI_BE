package com.back2basics.domain.notify.dto.request;

import com.back2basics.notify.model.NotificationType;

public record NotificationCreateRequest(String message, NotificationType type) {

}
