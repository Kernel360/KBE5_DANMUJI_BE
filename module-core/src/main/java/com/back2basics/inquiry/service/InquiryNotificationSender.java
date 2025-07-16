package com.back2basics.inquiry.service;

import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InquiryNotificationSender {

    private final NotifyUseCase notifyUseCase;
    private final UserQueryPort userQueryPort;

    public void sendNotification(Long senderId, Inquiry inquiry) {
        User admin = userQueryPort.findAdminByRole();

        if (!admin.getId().equals(senderId)) {
            notifyUseCase.notify(new SendNotificationCommand(
                admin.getId(),
                null,
                inquiry.getId(),
                NotificationType.INQUIRY_CREATED.getDescription(),
                NotificationType.INQUIRY_CREATED
            ));
        }
    }
}
