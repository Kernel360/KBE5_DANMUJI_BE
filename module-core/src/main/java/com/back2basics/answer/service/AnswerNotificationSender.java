package com.back2basics.answer.service;

import com.back2basics.answer.model.Answer;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerNotificationSender {

    private final NotifyUseCase notifyUseCase;

    public void sendNotification(Long senderId, Inquiry inquiry, Answer answer) {
        Long receiverId = inquiry.getAuthorId();

        if (!receiverId.equals(senderId)) {
            notifyUseCase.notify(new SendNotificationCommand(
                receiverId,
                null,
                answer.getId(),
                NotificationType.ANSWER_CREATED.getDescription(),
                NotificationType.ANSWER_CREATED
            ));
        }
    }
}