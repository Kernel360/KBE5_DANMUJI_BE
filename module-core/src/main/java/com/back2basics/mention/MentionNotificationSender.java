package com.back2basics.mention;

import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import com.back2basics.user.port.out.UserQueryPort;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MentionNotificationSender {

    private final UserQueryPort userQueryPort;
    private final NotifyUseCase notifyUseCase;

    public void notifyMentionedUsers(Long senderId, Long targetId, String content) {
        List<String> mentionedUsernames = MentionUtils.extractMentionUsernames(content);

        if (mentionedUsernames.isEmpty()) {
            return;
        }

        Map<String, Long> userMap = userQueryPort.findUserIdsByUsernames(mentionedUsernames);

        for (Long receiverId : userMap.values()) {

            if (Objects.equals(receiverId, senderId)) {
                continue;
            }

            notifyUseCase.notify(new SendNotificationCommand(
                receiverId,
                targetId,
                NotificationType.MENTIONED.getDescription(),
                NotificationType.MENTIONED
            ));
        }
    }
}
