package com.back2basics.assignment.service.notification;

import com.back2basics.notify.model.NotificationType;
import com.back2basics.infra.validation.validator.ProjectValidator;
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
            "새 프로젝트에 배정되었습니다.",
            NotificationType.PROJECT_CREATE_ASSIGNMENT
        )));
    }
}
