package com.back2basics.assignment.service.notification;

import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignmentNotificationSender {

    private final NotifyUseCase notifyUseCase;
    private final ProjectValidator projectValidator;

    public void sendNotification(List<Long> senderIds, Long projectId) {
        projectValidator.findById(projectId);
        senderIds.forEach(senderId -> notifyUseCase.notify(new SendNotificationCommand(
            senderId,
            projectId,
            null,
            NotificationType.PROJECT_CREATE_ASSIGNMENT.getDescription(),
            NotificationType.PROJECT_CREATE_ASSIGNMENT
        )));
    }
}
