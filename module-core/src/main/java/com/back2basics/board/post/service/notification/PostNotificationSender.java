package com.back2basics.board.post.service.notification;

import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.in.command.PostCreateCommand;
import com.back2basics.infra.validator.PostValidator;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import com.back2basics.project.port.out.ProjectMemberQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostNotificationSender {

    private final NotifyUseCase notifyUseCase;
    private final PostValidator postValidator;
    private final ProjectMemberQueryPort projectMemberQueryPort;

    public void sendNotification(Long senderId, Long postId, PostCreateCommand command) {

        // 답글인 경우:부모 게시글 작성자에게
        if (command.getParentId() != null) {
            Post parentPost = postValidator.findPost(command.getParentId());
            Long receiverId = parentPost.getAuthorId();

            if (!receiverId.equals(senderId)) {
                notifyUseCase.notify(new SendNotificationCommand(
                    receiverId,
                    parentPost.getProjectId(),
                    postId,
                    NotificationType.POST_REPLY_CREATED.getDescription(),
                    NotificationType.POST_REPLY_CREATED
                ));
            }
        }

        // 일반 게시글인 경우 : 프로젝트 참여자 모두에게 알림
        else {
            Long projectId = command.getProjectId();
            List<Long> memberIds = projectMemberQueryPort.getUserIdsByProject(projectId);

            for (Long receiverId : memberIds) {
                if (!receiverId.equals(senderId)) {
                    notifyUseCase.notify(new SendNotificationCommand(
                        receiverId,
                        command.getProjectId(),
                        postId,
                        NotificationType.PROJECT_POST_CREATED.getDescription(),
                        NotificationType.PROJECT_POST_CREATED
                    ));
                }
            }
        }
    }

    public void sendNotification(Long senderId, Long postId) {
        Post post = postValidator.findPost(postId);

        notifyUseCase.notify(new SendNotificationCommand(
            post.getAuthorId(),
            post.getProjectId(),
            postId,
            NotificationType.POST_RESTORED.getDescription(),
            NotificationType.POST_RESTORED
        ));
    }

}
